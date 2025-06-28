package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class RegistroCitas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Cita nuevaCita = new Cita();
            nuevaCita.setPacienteId(Integer.parseInt(request.getParameter("paciente_id")));
            nuevaCita.setDoctorId(Integer.parseInt(request.getParameter("doctor_id")));
            nuevaCita.setFecha(Date.valueOf(request.getParameter("fecha")));
            
            // Asegurar formato HH:mm:ss para la hora
            String horaParam = request.getParameter("hora");
            if (horaParam != null && horaParam.matches("\\d{2}:\\d{2}")) {
                horaParam += ":00";
            }
            nuevaCita.setHora(Time.valueOf(horaParam));
            
            nuevaCita.setTipo_consulta(request.getParameter("tipo_consulta"));
            nuevaCita.setMotivo(request.getParameter("motivo"));
            nuevaCita.setEstado("pendiente"); // Estado por defecto

            CitasDao citasDao = new CitasDao();
            citasDao.insertarCitaCompleta(nuevaCita);
            
            response.sendRedirect("usuario/citas.jsp?success=true");

        } catch (SQLException e) {
            request.setAttribute("error", "No se pudo registrar la cita. Error de base de datos.");
            throw new ServletException(e);
        } catch (Exception e) {
            request.setAttribute("error", "No se pudo registrar la cita. Datos inválidos.");
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CitasDao citasDao = new CitasDao();
        try {
            List<Cita> citas = citasDao.getAllCitas();
            request.setAttribute("citas", citas);
            request.getRequestDispatcher("citas.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

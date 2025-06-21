package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@WebServlet("/recepcionista/nueva-cita")
public class RecepcionistaNuevaCitaServlet extends HttpServlet {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final DoctorDao doctorDao = new DoctorDao();
    private final CitasDao citasDao = new CitasDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Usuario> pacientes = usuarioDao.getUsuariosByRol("paciente");
            List<Doctor> doctores = doctorDao.getAllDoctores();
            
            request.setAttribute("pacientes", pacientes);
            request.setAttribute("doctores", doctores);
            
            request.getRequestDispatcher("/recepcionista/nueva_cita.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al cargar datos para nueva cita", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Cita nuevaCita = new Cita();
            nuevaCita.setPacienteId(Integer.parseInt(request.getParameter("paciente_id")));
            nuevaCita.setDoctorId(Integer.parseInt(request.getParameter("doctor_id")));
            nuevaCita.setFecha(Date.valueOf(request.getParameter("fecha")));
            
            // Se añaden segundos al formato de la hora para que sea compatible con SQL Time
            String horaParam = request.getParameter("hora");
            if (horaParam.length() == 5) { // Formato HH:mm
                horaParam += ":00";
            }
            nuevaCita.setHora(Time.valueOf(horaParam));
            
            nuevaCita.setTipo_consulta(request.getParameter("tipo_consulta"));
            nuevaCita.setMotivo(request.getParameter("motivo"));
            nuevaCita.setSintomas(request.getParameter("sintomas"));
            nuevaCita.setEstado("pendiente"); // Las citas nuevas siempre están pendientes

            citasDao.insertarCitaCompleta(nuevaCita);

            request.getSession().setAttribute("successMessage", "¡Cita registrada exitosamente!");
            response.sendRedirect(request.getContextPath() + "/recepcionista/citas");
            
        } catch (SQLException e) {
            request.setAttribute("error", "Error en la base de datos al registrar la cita: " + e.getMessage());
            doGet(request, response); // Recargar el formulario con el mensaje de error
        } catch (Exception e) {
            request.setAttribute("error", "Error inesperado al procesar el formulario: " + e.getMessage());
            doGet(request, response); // Recargar el formulario con el mensaje de error
        }
    }
} 
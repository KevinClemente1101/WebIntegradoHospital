
package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class RegistroCitas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        int doctorId = Integer.parseInt(request.getParameter("doctor_id"));
        String fechaHoraStr = request.getParameter("fecha_hora");
        String estado = request.getParameter("estado");

        Timestamp fechaHora = Timestamp.valueOf(fechaHoraStr);

        Cita cita = new Cita();
        cita.setUsuarioId(usuarioId);
        cita.setDoctorId(doctorId);
        cita.setFechaHora(fechaHora);
        cita.setEstado(estado);

        CitasDao citasDao = new CitasDao();
        try {
            boolean exito = citasDao.insertCita(cita);
            if (exito) {
                response.sendRedirect("citas.jsp");
            } else {
                request.setAttribute("error", "No se pudo registrar la cita.");
                request.getRequestDispatcher("solicitar_cita.jsp").forward(request, response);
            }
        } catch (SQLException e) {
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

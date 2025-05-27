package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.dto.Cita;
import com.mycompany.hospital_citas.dao.CitasDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;

@WebServlet("/usuarios/cita")
public class CitaServlet extends HttpServlet {
    private final CitasDao dao = new CitasDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            switch (action) {
                case "view": {
                    Cita cita = dao.getCitaById(id);
                    req.setAttribute("cita", cita);
                    req.getRequestDispatcher("/usuario/view.jsp")
                       .forward(req, resp);
                    break;
                }
                case "edit": {
                    Cita cita = dao.getCitaById(id);
                    req.setAttribute("cita", cita);
                    req.getRequestDispatcher("/usuario/edit.jsp")
                       .forward(req, resp);
                    break;
                }
                case "cancel": {
                    // Leer la cita, cambiar estado y actualizar
                    Cita cita = dao.getCitaById(id);
                    cita.setEstado("cancelada");
                    dao.updateCita(cita);
                    resp.sendRedirect(req.getContextPath() + "/usuarios/dashboard");
                    break;
                }
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                   "Acción desconocida: " + action);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Solo para el formulario de edición
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int usuarioId = Integer.parseInt(req.getParameter("usuarioId"));
            int doctorId = Integer.parseInt(req.getParameter("doctorId"));
            Date fecha = Date.valueOf(req.getParameter("fecha"));
            String horaStr = req.getParameter("hora");
            if (horaStr != null && horaStr.length() == 5) { // formato HH:mm
                horaStr += ":00";
            }
            Time hora = Time.valueOf(horaStr);
            String estado = req.getParameter("estado");
            

            Cita cita = new Cita();
            cita.setId(id);
            cita.setUsuarioId(usuarioId);
            cita.setDoctorId(doctorId);
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setEstado(estado);

            boolean updated = dao.updateCita(cita);
            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/usuarios/dashboard");
                return;
            } else {
                req.setAttribute("error", "No se pudo actualizar la cita.");
                req.getRequestDispatcher("/usuario/edit.jsp").forward(req, resp);
                return;
            }

            
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}

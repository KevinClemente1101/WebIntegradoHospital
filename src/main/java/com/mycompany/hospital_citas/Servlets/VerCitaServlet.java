package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/doctor/ver-cita")
public class VerCitaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/dashboard");
            return;
        }
        try {
            int citaId = Integer.parseInt(idParam);
            CitasDao citasDao = new CitasDao();
            Cita cita = citasDao.getCitaById(citaId);
            if (cita == null) {
                request.setAttribute("error", "No se encontró la cita.");
            } else {
                request.setAttribute("cita", cita);
            }
            request.getRequestDispatcher("/doctor/ver_cita.jsp").forward(request, response);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al obtener la cita", e);
        }
    }
} 
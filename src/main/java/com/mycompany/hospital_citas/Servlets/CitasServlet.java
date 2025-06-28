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
import java.util.List;

@WebServlet("/admin/citas")
public class CitasServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CitasDao citasDao = new CitasDao();
        try {
            List<Cita> citas = citasDao.getAllCitasConNombres();
            request.setAttribute("citas", citas);
            request.getRequestDispatcher("citas.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener la lista de citas", e);
        }
    }
}

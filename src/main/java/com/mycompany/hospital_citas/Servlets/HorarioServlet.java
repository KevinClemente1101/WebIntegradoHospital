package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Horario;
import com.mycompany.hospital_citas.HorarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/horarios")
public class HorarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HorarioDao horarioDao = new HorarioDao();
        try {
            List<Horario> horarios = horarioDao.getAllHorariosConMedico();
            request.setAttribute("horarios", horarios);
            request.getRequestDispatcher("horarios.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener la lista de horarios", e);
        }
    }
}

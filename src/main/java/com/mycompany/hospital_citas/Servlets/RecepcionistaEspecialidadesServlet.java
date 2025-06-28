package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.EspecialidadDao;
import com.mycompany.hospital_citas.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/recepcionista/especialidades")
public class RecepcionistaEspecialidadesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"recepcionista".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            EspecialidadDao especialidadDao = new EspecialidadDao();
            List<Especialidad> especialidades = especialidadDao.getAllEspecialidades();
            request.setAttribute("especialidades", especialidades);
            request.getRequestDispatcher("/recepcionista/especialidades.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al consultar la base de datos para obtener especialidades", e);
        }
    }
} 
package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/pacientes")
public class AdminPacientesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"admin".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            UsuarioDao usuarioDao = new UsuarioDao();
            List<Usuario> pacientes = usuarioDao.getUsuariosByRol("paciente");
            request.setAttribute("pacientes", pacientes);
            request.getRequestDispatcher("/admin/pacientes.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al consultar la base de datos para obtener pacientes", e);
        }
    }
} 
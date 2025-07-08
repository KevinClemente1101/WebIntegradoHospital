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

@WebServlet("/admin/cambiar-estado-paciente")
public class AdminCambiarEstadoPacienteServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"admin".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int pacienteId = Integer.parseInt(request.getParameter("id"));
            String accion = request.getParameter("accion"); // "activar" o "desactivar"

            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/admin/pacientes");
                return;
            }

            boolean nuevoEstado;
            String mensaje;
            
            if ("activar".equals(accion)) {
                nuevoEstado = true;
                mensaje = "Paciente activado correctamente.";
            } else if ("desactivar".equals(accion)) {
                nuevoEstado = false;
                mensaje = "Paciente desactivado correctamente.";
            } else {
                request.setAttribute("error", "Acción no válida.");
                response.sendRedirect(request.getContextPath() + "/admin/pacientes");
                return;
            }

            paciente.setEstado(nuevoEstado);
            boolean actualizado = usuarioDao.updateUsuario(paciente);
            
            if (actualizado) {
                request.setAttribute("success", mensaje);
            } else {
                request.setAttribute("error", "No se pudo cambiar el estado del paciente.");
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/pacientes");
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cambiar el estado del paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/pacientes");
        }
    }
} 
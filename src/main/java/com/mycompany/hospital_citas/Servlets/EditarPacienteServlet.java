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

@WebServlet("/recepcionista/editar-paciente")
public class EditarPacienteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"recepcionista".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
            return;
        }

        try {
            int pacienteId = Integer.parseInt(idParam);
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
                return;
            }
            
            request.setAttribute("paciente", paciente);
            request.getRequestDispatcher("/recepcionista/editar_paciente.jsp").forward(request, response);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los datos del paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"recepcionista".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int pacienteId = Integer.parseInt(request.getParameter("id"));
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String email = request.getParameter("email");

            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
                return;
            }

            // Actualizar solo los campos permitidos
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setEmail(email);

            boolean actualizado = usuarioDao.updateUsuario(paciente);
            
            if (actualizado) {
                request.setAttribute("success", "Datos del paciente actualizados correctamente.");
            } else {
                request.setAttribute("error", "No se pudieron actualizar los datos del paciente.");
            }
            
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar los datos del paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
        }
    }
} 
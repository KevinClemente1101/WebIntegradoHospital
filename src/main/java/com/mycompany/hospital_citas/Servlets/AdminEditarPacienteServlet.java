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

@WebServlet("/admin/editar-paciente")
public class AdminEditarPacienteServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"admin".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/pacientes");
            return;
        }

        try {
            int pacienteId = Integer.parseInt(idParam);
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/admin/pacientes");
                return;
            }
            
            request.setAttribute("paciente", paciente);
            request.getRequestDispatcher("/admin/editar_paciente.jsp").forward(request, response);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los datos del paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/pacientes");
        }
    }

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
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String email = request.getParameter("email");
            String tipo_sangre = request.getParameter("tipo_sangre");
            String alergias = request.getParameter("alergias");
            String enfermedades_cronicas = request.getParameter("enfermedades_cronicas");

            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/admin/pacientes");
                return;
            }

            // Actualizar los campos permitidos
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setEmail(email);
            paciente.setTipo_sangre(tipo_sangre);
            paciente.setAlergias(alergias);
            paciente.setEnfermedades_cronicas(enfermedades_cronicas);

            boolean actualizado = usuarioDao.updateUsuario(paciente);
            
            if (actualizado) {
                request.setAttribute("success", "Datos del paciente actualizados correctamente.");
            } else {
                request.setAttribute("error", "No se pudieron actualizar los datos del paciente.");
            }
            
            response.sendRedirect(request.getContextPath() + "/admin/pacientes");
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar los datos del paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/pacientes");
        }
    }
} 
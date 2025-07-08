package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Cita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/recepcionista/eliminar-paciente")
public class EliminarPacienteServlet extends HttpServlet {
    
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
            CitasDao citasDao = new CitasDao();
            
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
                return;
            }
            
            // Verificar si el paciente tiene citas activas
            List<Cita> citasPaciente = citasDao.getCitasByPacienteId(pacienteId);
            boolean tieneCitasActivas = citasPaciente.stream()
                .anyMatch(cita -> "pendiente".equalsIgnoreCase(cita.getEstado()) || 
                                "confirmada".equalsIgnoreCase(cita.getEstado()));
            
            request.setAttribute("paciente", paciente);
            request.setAttribute("citasPaciente", citasPaciente);
            request.setAttribute("tieneCitasActivas", tieneCitasActivas);
            request.getRequestDispatcher("/recepcionista/confirmar_eliminar_paciente.jsp").forward(request, response);
            
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
            String confirmacion = request.getParameter("confirmacion");
            
            if (!"ELIMINAR".equals(confirmacion)) {
                request.setAttribute("error", "Confirmación incorrecta. El paciente no fue eliminado.");
                response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
                return;
            }

            UsuarioDao usuarioDao = new UsuarioDao();
            CitasDao citasDao = new CitasDao();
            
            Usuario paciente = usuarioDao.getUsuarioById(pacienteId);
            
            if (paciente == null || !"paciente".equals(paciente.getRol())) {
                request.setAttribute("error", "Paciente no encontrado.");
                response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
                return;
            }

            // Verificar si el paciente tiene citas activas
            List<Cita> citasPaciente = citasDao.getCitasByPacienteId(pacienteId);
            boolean tieneCitasActivas = citasPaciente.stream()
                .anyMatch(cita -> "pendiente".equalsIgnoreCase(cita.getEstado()) || 
                                "confirmada".equalsIgnoreCase(cita.getEstado()));
            
            if (tieneCitasActivas) {
                request.setAttribute("error", "No se puede eliminar el paciente porque tiene citas activas pendientes o confirmadas.");
                response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
                return;
            }

            // Eliminar el paciente
            boolean eliminado = usuarioDao.deleteUsuario(pacienteId);
            
            if (eliminado) {
                request.setAttribute("success", "Paciente eliminado correctamente.");
            } else {
                request.setAttribute("error", "No se pudo eliminar el paciente.");
            }
            
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al eliminar el paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
        }
    }
} 
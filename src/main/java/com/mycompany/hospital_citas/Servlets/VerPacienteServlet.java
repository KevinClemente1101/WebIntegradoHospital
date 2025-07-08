package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/recepcionista/ver-paciente")
public class VerPacienteServlet extends HttpServlet {
    
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
            
            // Obtener el historial de citas del paciente
            List<Cita> citasPaciente = citasDao.getCitasByPacienteId(pacienteId);
            
            request.setAttribute("paciente", paciente);
            request.setAttribute("citasPaciente", citasPaciente);
            request.getRequestDispatcher("/recepcionista/ver_paciente.jsp").forward(request, response);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los datos del paciente: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/recepcionista/pacientes");
        }
    }
} 
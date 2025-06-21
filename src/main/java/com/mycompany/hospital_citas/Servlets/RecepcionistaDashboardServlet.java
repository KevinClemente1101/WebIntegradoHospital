package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
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
import java.time.LocalDate;
import java.util.List;

@WebServlet("/recepcionista/dashboard")
public class RecepcionistaDashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !"recepcionista".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            CitasDao citasDao = new CitasDao();
            DoctorDao doctorDao = new DoctorDao();
            UsuarioDao usuarioDao = new UsuarioDao();
            
            // Obtener estadísticas
            LocalDate hoy = LocalDate.now();
            int citasHoy = citasDao.getCitasByDate(hoy);
            int citasPendientes = citasDao.getCitasByEstado("pendiente");
            int totalPacientes = usuarioDao.countUsuariosByRol("paciente");
            int doctoresActivos = doctorDao.countDoctoresActivos();
            
            // Obtener próximas citas (próximos 7 días)
            List<Cita> proximasCitas = citasDao.getProximasCitas(7);
            
            // Establecer atributos
            request.setAttribute("usuario", usuario);
            request.setAttribute("citasHoy", citasHoy);
            request.setAttribute("citasPendientes", citasPendientes);
            request.setAttribute("totalPacientes", totalPacientes);
            request.setAttribute("doctoresActivos", doctoresActivos);
            request.setAttribute("proximasCitas", proximasCitas);
            
            request.getRequestDispatcher("/recepcionista/dashboard.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error al cargar el dashboard", e);
        }
    }
} 
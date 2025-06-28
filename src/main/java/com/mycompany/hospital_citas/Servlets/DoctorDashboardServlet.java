package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Usuario;
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
import java.util.stream.Collectors;

@WebServlet("/doctor/dashboard")
public class DoctorDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
        if (usuario == null || !"doctor".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        try {
            DoctorDao doctorDao = new DoctorDao();
            Doctor doctor = doctorDao.getDoctorByUsuarioId(usuario.getId());
            if (doctor == null) {
                request.setAttribute("errorMessage", "No se encontró el perfil de doctor asociado a este usuario.");
                request.getRequestDispatcher("/doctor/dashboardD.jsp").forward(request, response);
                return;
            }
            CitasDao citasDao = new CitasDao();
            List<Cita> todasCitas = citasDao.getCitasByDoctorId(doctor.getId());
            LocalDate hoy = LocalDate.now();
            // Citas de hoy
            long citasHoy = todasCitas.stream().filter(c -> c.getFecha() != null && c.getFecha().toLocalDate().equals(hoy)).count();
            // Citas pendientes
            long citasPendientes = todasCitas.stream().filter(c -> "pendiente".equalsIgnoreCase(c.getEstado())).count();
            // Total citas
            long totalCitas = todasCitas.size();
            // Próximas citas (hoy o futuras, máximo 5)
            List<Cita> proximasCitas = todasCitas.stream()
                .filter(c -> c.getFecha() != null && !c.getFecha().toLocalDate().isBefore(hoy))
                .sorted((a, b) -> a.getFecha().compareTo(b.getFecha()))
                .limit(5)
                .collect(Collectors.toList());
            request.setAttribute("citasHoy", citasHoy);
            request.setAttribute("citasPendientes", citasPendientes);
            request.setAttribute("totalCitas", totalCitas);
            request.setAttribute("proximasCitas", proximasCitas);
            request.getRequestDispatcher("/doctor/dashboardD.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener el dashboard del doctor", e);
        }
    }
} 
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
import java.util.List;

@WebServlet("/doctor/citas")
public class DoctorCitasServlet extends HttpServlet {
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
                request.getRequestDispatcher("/doctor/citasD.jsp").forward(request, response);
                return;
            }
            CitasDao citasDao = new CitasDao();
            List<Cita> citas = citasDao.getCitasByDoctorId(doctor.getId());
            System.out.println("[DEBUG] Citas encontradas para el doctor: " + citas.size());
            for (Cita c : citas) {
                System.out.println("[DEBUG] Paciente: " + c.getPacienteNombre() + ", Fecha: " + c.getFechaHora() + ", Estado: " + c.getEstado());
            }
            request.setAttribute("citas", citas);
            request.getRequestDispatcher("/doctor/citasD.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener las citas del doctor", e);
        }
    }
} 
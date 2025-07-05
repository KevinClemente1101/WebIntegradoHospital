package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.util.EmailUtil;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Doctor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.mail.MessagingException;

@WebServlet("/admin/cancelarCitaAdmin")
public class CancelarCitaAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/citas");
            return;
        }
        request.setAttribute("citaId", idParam);
        request.getRequestDispatcher("/admin/cancelarCitaMotivo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("cita_id");
        String motivo = request.getParameter("motivo");
        if (idParam == null || motivo == null || motivo.trim().isEmpty()) {
            request.setAttribute("error", "Debes ingresar un motivo de cancelación.");
            request.setAttribute("citaId", idParam);
            request.getRequestDispatcher("/admin/cancelarCitaMotivo.jsp").forward(request, response);
            return;
        }
        try {
            int citaId = Integer.parseInt(idParam);
            CitasDao citasDao = new CitasDao();
            Cita cita = citasDao.getCitaById(citaId);
            // Cambiar estado y guardar motivo
            String sql = "UPDATE citas SET estado='cancelada', observaciones=? WHERE id=?";
            try (var conn = com.mycompany.hospital_citas.DBUtil.getConnection();
                 var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, motivo);
                stmt.setInt(2, citaId);
                stmt.executeUpdate();
            }
            // Obtener email del paciente
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(cita.getPacienteId());
            // Enviar correo
            String subject = "Cita Cancelada";
            String body = "Su cita ha sido cancelada. Motivo: " + motivo;
            EmailUtil.enviarCorreo(paciente.getEmail(), subject, body);
            // Obtener email del doctor
            DoctorDao doctorDao = new DoctorDao();
            Doctor doctor = doctorDao.getDoctorById(cita.getDoctorId());
            Usuario usuarioDoctor = usuarioDao.getUsuarioById(doctor.getUsuarioId());
            EmailUtil.enviarCorreo(usuarioDoctor.getEmail(), subject, body);
            response.sendRedirect(request.getContextPath() + "/admin/citas?cancelada=1");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cancelar la cita: " + e.getMessage());
            request.setAttribute("citaId", idParam);
            request.getRequestDispatcher("/admin/cancelarCitaMotivo.jsp").forward(request, response);
        }
    }
} 
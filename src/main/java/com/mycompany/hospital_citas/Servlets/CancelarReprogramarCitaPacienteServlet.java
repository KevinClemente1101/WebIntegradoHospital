package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@WebServlet("/usuario/cancelarReprogramarCitaPaciente")
public class CancelarReprogramarCitaPacienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/usuario/citas");
            return;
        }
        try {
            int citaId = Integer.parseInt(idParam);
            CitasDao citasDao = new CitasDao();
            Cita cita = citasDao.getCitaById(citaId);
            // Calcular diferencia de tiempo
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaCita = LocalDateTime.of(cita.getFecha().toLocalDate(), cita.getHora().toLocalTime());
            long minutos = ChronoUnit.MINUTES.between(ahora, fechaCita);
            if (minutos < 60) {
                request.setAttribute("error", "No puedes cancelar la cita con menos de 1 hora de anticipación.");
                request.getRequestDispatcher("/usuario/citas.jsp").forward(request, response);
                return;
            }
            request.setAttribute("cita", cita);
            request.getRequestDispatcher("/usuario/cancelarReprogramarCita.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/usuario/citas");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String citaIdParam = request.getParameter("cita_id");
        String motivo = request.getParameter("motivo");
        String accion = request.getParameter("accion");
        if (citaIdParam == null || motivo == null || motivo.trim().isEmpty()) {
            request.setAttribute("error", "Debes ingresar un motivo.");
            request.setAttribute("citaId", citaIdParam);
            request.getRequestDispatcher("/usuario/cancelarReprogramarCita.jsp").forward(request, response);
            return;
        }
        try {
            int citaId = Integer.parseInt(citaIdParam);
            CitasDao citasDao = new CitasDao();
            Cita cita = citasDao.getCitaById(citaId);
            UsuarioDao usuarioDao = new UsuarioDao();
            Usuario paciente = usuarioDao.getUsuarioById(cita.getPacienteId());
            DoctorDao doctorDao = new DoctorDao();
            Doctor doctor = doctorDao.getDoctorById(cita.getDoctorId());
            Usuario usuarioDoctor = usuarioDao.getUsuarioById(doctor.getUsuarioId());
            String subject = "Cita Cancelada";
            String body = "Su cita ha sido cancelada por el paciente. Motivo: " + motivo;
            if ("cancelar".equals(accion)) {
                // Cancelar la cita
                String sql = "UPDATE citas SET estado='cancelada', observaciones=? WHERE id=?";
                try (var conn = com.mycompany.hospital_citas.DBUtil.getConnection();
                     var stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, motivo);
                    stmt.setInt(2, citaId);
                    stmt.executeUpdate();
                }
                EmailUtil.enviarCorreo(usuarioDoctor.getEmail(), subject, body);
                EmailUtil.enviarCorreo(paciente.getEmail(), subject, body);
                response.sendRedirect(request.getContextPath() + "/usuario/citas?cancelada=1");
            } else if ("reprogramar".equals(accion)) {
                // Redirigir a formulario de reprogramación
                request.setAttribute("cita", cita);
                request.setAttribute("motivo", motivo);
                request.getRequestDispatcher("/usuario/reprogramarCita.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cancelar/reprogramar la cita: " + e.getMessage());
            request.getRequestDispatcher("/usuario/cancelarReprogramarCita.jsp").forward(request, response);
        }
    }
} 
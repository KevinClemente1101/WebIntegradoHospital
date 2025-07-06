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
        String nuevaFecha = request.getParameter("fecha");
        String nuevaHora = request.getParameter("hora");
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
            } else if (nuevaFecha != null && nuevaHora != null) {
                // Procesar la reprogramación
                java.sql.Date sqlFecha = java.sql.Date.valueOf(nuevaFecha);
                java.sql.Time sqlHora = java.sql.Time.valueOf(nuevaHora.length() == 5 ? nuevaHora + ":00" : nuevaHora);
                // Validar que la hora esté disponible
                List<java.time.LocalTime> horasOcupadas = citasDao.getHorasOcupadas(doctor.getId(), sqlFecha);
                if (horasOcupadas.contains(sqlHora.toLocalTime())) {
                    request.setAttribute("error", "La hora seleccionada ya está ocupada. Por favor, elige otra.");
                    request.setAttribute("cita", cita);
                    request.setAttribute("motivo", motivo);
                    request.getRequestDispatcher("/usuario/reprogramarCita.jsp").forward(request, response);
                    return;
                }
                cita.setFecha(sqlFecha);
                cita.setHora(sqlHora);
                cita.setEstado("pendiente");
                citasDao.updateCita(cita);
                // Notificar por correo
                String subjectReprog = "Cita Reprogramada";
                String bodyReprog = "Su cita ha sido reprogramada por el paciente. Motivo: " + motivo + ". Nueva fecha: " + nuevaFecha + " " + nuevaHora;
                EmailUtil.enviarCorreo(usuarioDoctor.getEmail(), subjectReprog, bodyReprog);
                EmailUtil.enviarCorreo(paciente.getEmail(), subjectReprog, bodyReprog);
                response.sendRedirect(request.getContextPath() + "/usuario/citas?reprogramada=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (nuevaFecha != null && nuevaHora != null) {
                request.setAttribute("error", "Error al reprogramar la cita: " + e.getMessage());
                request.getRequestDispatcher("/usuario/reprogramarCita.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error al cancelar/reprogramar la cita: " + e.getMessage());
                request.getRequestDispatcher("/usuario/cancelarReprogramarCita.jsp").forward(request, response);
            }
        }
    }
} 
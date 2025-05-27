package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.dto.Cita;
import com.mycompany.hospital_citas.dao.CitasDao;
import com.mycompany.hospital_citas.dto.UsuarioDTO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

@WebServlet("/usuario/agendar")
public class AgendarCitaServlet extends HttpServlet {
    private final CitasDao dao = new CitasDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Paso 1: cargar especialidades
            req.setAttribute("especialidades", dao.getAllEspecialidades());

            // Si seleccionó especialidad, cargar médicos
            String espId = req.getParameter("especialidad_id");
            if (espId != null) {
                int e = Integer.parseInt(espId);
                req.setAttribute("medicos", dao.getMedicosByEspecialidad(e));
            }
            // Si seleccionó médico, cargar horarios
            String medId = req.getParameter("medico_id");
            if (medId != null) {
                int m = Integer.parseInt(medId);
                req.setAttribute("horarios", dao.getHorariosByMedico(m));
            }

            // Fecha mínima hoy para el JSP
            req.setAttribute("today", LocalDate.now());

            req.getRequestDispatcher("/paciente/agendar.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Crear la cita
        try {
            int pacienteId = ((UsuarioDTO)req.getSession().getAttribute("usuario")).getId();
            int medicoId   = Integer.parseInt(req.getParameter("medico_id"));
            Date fecha     = Date.valueOf(req.getParameter("fecha"));
            Time hora      = Time.valueOf(req.getParameter("hora") + ":00");
            String motivo  = req.getParameter("motivo");

            Cita cita = new Cita();
            cita.setUsuarioId(pacienteId);
            cita.setDoctorId(medicoId);
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setEstado("pendiente");
            cita.setMotivo(motivo);

            boolean ok = dao.insertCita(cita);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/usuario/dashboard");
            } else {
                req.setAttribute("error", "No se pudo agendar la cita");
                doGet(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}

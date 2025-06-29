package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Horario;
import com.mycompany.hospital_citas.HorarioDao;
import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.HorarioAgrupado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/doctor/horarios")
public class DoctorHorarioServlet extends HttpServlet {

    private final HorarioDao horarioDao = new HorarioDao();
    private final DoctorDao doctorDao = new DoctorDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"doctor".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Manejo de mensajes Flash (para que se muestren solo una vez)
        if (session != null) {
            if (session.getAttribute("successMessage") != null) {
                request.setAttribute("successMessage", session.getAttribute("successMessage"));
                session.removeAttribute("successMessage");
            }
            if (session.getAttribute("errorMessage") != null) {
                request.setAttribute("errorMessage", session.getAttribute("errorMessage"));
                session.removeAttribute("errorMessage");
            }
        }

        try {
            // Asumiendo que el ID de usuario corresponde a un doctor y necesitamos el ID de
            // la tabla 'medicos'
            Doctor doctor = doctorDao.getDoctorByUsuarioId(usuario.getId());
            if (doctor == null) {
                request.setAttribute("errorMessage", "No se encontró el perfil de doctor asociado a este usuario.");
                request.getRequestDispatcher("/doctor/horariosD.jsp").forward(request, response);
                return;
            }

            List<Horario> horariosDB = horarioDao.getHorariosByDoctorId(doctor.getId());
            // List<HorarioAgrupado> horariosAgrupados = agruparHorarios(horariosDB);
            // System.out.println("[DEBUG] Horarios agrupados enviados al JSP: " +
            // horariosAgrupados.size());
            request.setAttribute("horarios", horariosDB);
            request.getRequestDispatcher("/doctor/horariosD.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar la base de datos para obtener los horarios.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null || !"doctor".equals(usuario.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso no autorizado.");
            return;
        }

        try {
            Doctor doctor = doctorDao.getDoctorByUsuarioId(usuario.getId());
            if (doctor == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Perfil de doctor no encontrado.");
                return;
            }

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                handleDelete(request, response);
            } else {
                handlePost(request, response, doctor);
            }

        } catch (SQLException e) {
            throw new ServletException("Error de base de datos en la operación de horario.", e);
        }
    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response, Doctor doctor)
            throws IOException, ServletException, SQLException {
        String diaSemana = request.getParameter("diaSemana");
        java.sql.Time horaInicio = java.sql.Time.valueOf(request.getParameter("hora_inicio") + ":00");
        java.sql.Time horaFin = java.sql.Time.valueOf(request.getParameter("hora_fin") + ":00");

        if (horaInicio.after(horaFin) || horaInicio.equals(horaFin)) {
            request.getSession().setAttribute("errorMessage",
                    "La hora de inicio no puede ser posterior o igual a la hora de fin.");
            response.sendRedirect(request.getContextPath() + "/doctor/horarios");
            return;
        }
        if (horarioDao.verificarTraslape(doctor.getId(), diaSemana, horaInicio, horaFin)) {
            request.getSession().setAttribute("errorMessage",
                    "El horario se solapa con otro existente en el rango de fechas y horas.");
            response.sendRedirect(request.getContextPath() + "/doctor/horarios");
            return;
        }
        Horario nuevoHorario = new Horario();
        nuevoHorario.setDoctor_id(doctor.getId());
        nuevoHorario.setDiaSemana(diaSemana);
        nuevoHorario.setHora_inicio(horaInicio);
        nuevoHorario.setHora_fin(horaFin);
        horarioDao.insertHorario(nuevoHorario);
        request.getSession().setAttribute("successMessage", "¡Horario añadido exitosamente!");
        response.sendRedirect(request.getContextPath() + "/doctor/horarios");
    }

    private List<HorarioAgrupado> agruparHorarios(List<Horario> horariosDB) {
        if (horariosDB == null || horariosDB.isEmpty()) {
            System.out.println("[DEBUG] agruparHorarios: lista vacía");
            return new ArrayList<>();
        }

        List<HorarioAgrupado> horariosAgrupados = new ArrayList<>();
        HorarioAgrupado grupoActual = null;
        Horario horarioAnterior = null;

        for (Horario horarioActual : horariosDB) {
            boolean esConsecutivo = horarioAnterior != null &&
                    horarioActual.getHora_inicio().equals(horarioAnterior.getHora_inicio()) &&
                    horarioActual.getHora_fin().equals(horarioAnterior.getHora_fin());

            if (esConsecutivo) {
                grupoActual.addId(horarioActual.getId());
            } else {
                if (grupoActual != null) {
                    horariosAgrupados.add(grupoActual);
                }
                grupoActual = new HorarioAgrupado();
                grupoActual.setHoraInicio(horarioActual.getHora_inicio());
                grupoActual.setHoraFin(horarioActual.getHora_fin());
                grupoActual.addId(horarioActual.getId());
            }
            horarioAnterior = horarioActual;
        }

        // Siempre agregar el último grupo si existe
        if (grupoActual != null && !horariosAgrupados.contains(grupoActual)) {
            horariosAgrupados.add(grupoActual);
        }

        System.out.println("[DEBUG] Horarios agrupados: " + horariosAgrupados.size());
        return horariosAgrupados;
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {

        String idsParaBorrar = request.getParameter("horario_ids");
        if (idsParaBorrar != null && !idsParaBorrar.isEmpty()) {
            String[] idsArray = idsParaBorrar.split(",");
            for (String idStr : idsArray) {
                horarioDao.deleteHorario(Integer.parseInt(idStr.trim()));
            }
        }

        request.getSession().setAttribute("successMessage", "Horario(s) eliminado(s) exitosamente.");
        response.sendRedirect(request.getContextPath() + "/doctor/horarios");
    }
}
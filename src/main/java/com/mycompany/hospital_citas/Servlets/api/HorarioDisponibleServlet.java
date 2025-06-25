package com.mycompany.hospital_citas.Servlets.api;

import com.google.gson.Gson;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Horario;
import com.mycompany.hospital_citas.HorarioDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet({"/api/horarios-disponibles", "/api/doctor-fechas-disponibles"})
public class HorarioDisponibleServlet extends HttpServlet {

    private final HorarioDao horarioDao = new HorarioDao();
    private final CitasDao citaDao = new CitasDao();
    private final Gson gson = new Gson();

    // Mapa para traducir días de la semana de forma consistente
    private static final Map<DayOfWeek, String> DIAS_SEMANA_MAP = Map.of(
        DayOfWeek.MONDAY, "Lunes",
        DayOfWeek.TUESDAY, "Martes",
        DayOfWeek.WEDNESDAY, "Miércoles",
        DayOfWeek.THURSDAY, "Jueves",
        DayOfWeek.FRIDAY, "Viernes",
        DayOfWeek.SATURDAY, "Sábado",
        DayOfWeek.SUNDAY, "Domingo"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/api/doctor-fechas-disponibles".equals(servletPath)) {
            handleFechasDisponibles(request, response);
        } else {
            handleHorariosDisponibles(request, response);
        }
    }

    private void handleFechasDisponibles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            List<Horario> horarios = horarioDao.getHorariosByDoctorId(doctorId);
            List<java.util.Map<String, String>> rangos = new ArrayList<>();
            for (Horario h : horarios) {
                java.util.Map<String, String> rango = new java.util.HashMap<>();
                rango.put("fecha_inicio", h.getFecha_inicio().toString());
                rango.put("fecha_fin", h.getFecha_fin().toString());
                rangos.add(rango);
            }
            out.print(gson.toJson(rangos));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(gson.toJson("Error: " + e.getMessage()));
        } finally {
            out.flush();
        }
    }

    private void handleHorariosDisponibles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            String fechaStr = request.getParameter("fecha");
            System.out.println("[DEBUG] doctorId=" + doctorId + ", fecha=" + fechaStr);
            LocalDate fecha = LocalDate.parse(fechaStr);
            List<Horario> horariosSemanales = horarioDao.getHorariosByDoctorId(doctorId);
            System.out.println("[DEBUG] horarios encontrados: " + horariosSemanales.size());
            Optional<Horario> horarioDelDia = horariosSemanales.stream()
                .filter(h -> !fecha.isBefore(h.getFecha_inicio().toLocalDate()) && !fecha.isAfter(h.getFecha_fin().toLocalDate()))
                .findFirst();
            if (!horarioDelDia.isPresent()) {
                System.out.println("[DEBUG] No hay horario para la fecha seleccionada");
            }
            List<String> slotsDisponibles = new ArrayList<>();
            if (horarioDelDia.isPresent()) {
                Horario horario = horarioDelDia.get();
                LocalTime horaInicio = horario.getHora_inicio().toLocalTime();
                LocalTime horaFin = horario.getHora_fin().toLocalTime();
                int intervalo = horario.getIntervalo_citas();
                if (intervalo <= 0) intervalo = 30; // Valor por defecto
                List<LocalTime> horasOcupadas = citaDao.getHorasOcupadas(doctorId, Date.valueOf(fecha));
                LocalTime slotActual = horaInicio;
                while (slotActual.isBefore(horaFin)) {
                    if (!horasOcupadas.contains(slotActual)) {
                        slotsDisponibles.add(slotActual.toString());
                    }
                    slotActual = slotActual.plusMinutes(intervalo);
                }
            }
            out.print(gson.toJson(slotsDisponibles));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(gson.toJson("Error: El ID del doctor debe ser un número."));
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("Error interno del servidor: " + e.getMessage()));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
} 
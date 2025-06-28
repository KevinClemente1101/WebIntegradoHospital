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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet({ "/api/horarios-disponibles", "/api/doctor-fechas-disponibles" })
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
            DayOfWeek.SUNDAY, "Domingo");

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

    private void handleHorariosDisponibles(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Validar parámetros de entrada
            String doctorIdStr = request.getParameter("doctorId");
            String fechaStr = request.getParameter("fecha");

            if (doctorIdStr == null || fechaStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson("Error: Se requieren doctorId y fecha"));
                return;
            }

            int doctorId = Integer.parseInt(doctorIdStr);
            LocalDate fecha = LocalDate.parse(fechaStr);

            System.out.println("[DEBUG] doctorId=" + doctorId + ", fecha=" + fechaStr);

            // Obtener horarios del doctor que estén vigentes para la fecha solicitada
            List<Horario> horariosDisponibles = horarioDao.getHorariosByDoctorIdAndDay(doctorId, null);
            System.out.println("[DEBUG] horarios encontrados: " + horariosDisponibles.size());

            List<String> slotsDisponibles = new ArrayList<>();

            if (!horariosDisponibles.isEmpty()) {
                // Obtener horas ocupadas para esa fecha
                List<LocalTime> horasOcupadas = citaDao.getHorasOcupadas(doctorId, Date.valueOf(fecha));
                System.out.println("[DEBUG] horas ocupadas: " + horasOcupadas.size());

                // Procesar cada horario disponible
                for (Horario horario : horariosDisponibles) {
                    // Verificar que la fecha solicitada esté dentro del rango del horario
                    LocalDate fechaInicio = horario.getFecha_inicio().toLocalDate();
                    LocalDate fechaFin = horario.getFecha_fin().toLocalDate();

                    if (fecha.isBefore(fechaInicio) || fecha.isAfter(fechaFin)) {
                        continue; // Saltar este horario si la fecha no está en el rango
                    }

                    LocalTime horaInicio = horario.getHora_inicio().toLocalTime();
                    LocalTime horaFin = horario.getHora_fin().toLocalTime();
                    int intervalo = horario.getIntervalo_citas();

                    // Validar intervalo
                    if (intervalo <= 0) {
                        intervalo = 30; // Valor por defecto
                    }

                    // Validar que el intervalo no sea mayor que la duración total
                    long duracionMinutos = java.time.Duration.between(horaInicio, horaFin).toMinutes();
                    if (intervalo > duracionMinutos) {
                        System.out.println("[WARNING] Intervalo mayor que duración total, usando 30 minutos");
                        intervalo = 30;
                    }

                    // Generar slots de tiempo con límite de seguridad
                    LocalTime slotActual = horaInicio;
                    int maxSlots = (int) (duracionMinutos / intervalo) + 1; // Límite máximo de slots
                    int slotCount = 0;

                    System.out.println("[DEBUG] Generando slots desde " + horaInicio + " hasta " + horaFin +
                            " con intervalo " + intervalo + " minutos, máximo " + maxSlots + " slots");

                    while (slotActual.isBefore(horaFin) && slotCount < maxSlots) {
                        // Verificar si el slot está disponible
                        if (!horasOcupadas.contains(slotActual)) {
                            slotsDisponibles
                                    .add(slotActual.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
                        }

                        // Avanzar al siguiente slot
                        slotActual = slotActual.plusMinutes(intervalo);
                        slotCount++;

                        // Verificación de seguridad adicional
                        if (slotCount > 100) {
                            System.out.println("[WARNING] Demasiados slots generados, deteniendo por seguridad");
                            break;
                        }
                    }
                }
            } else {
                System.out.println("[DEBUG] No hay horarios configurados para el doctor");
            }

            System.out.println("[DEBUG] Total slots disponibles: " + slotsDisponibles.size());
            out.print(gson.toJson(slotsDisponibles));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(gson.toJson("Error: El ID del doctor debe ser un número válido."));
            e.printStackTrace();
        } catch (java.time.format.DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(gson.toJson("Error: Formato de fecha inválido. Use YYYY-MM-DD"));
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
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

@WebServlet("/api/horarios-disponibles")
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
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            String fechaStr = request.getParameter("fecha");
            LocalDate fecha = LocalDate.parse(fechaStr);

            // 1. Obtener todos los horarios semanales del doctor
            List<Horario> horariosSemanales = horarioDao.getHorariosByDoctorId(doctorId);

            // 2. Encontrar el horario que corresponde al día de la semana de la fecha seleccionada
            DayOfWeek diaDeLaSemana = fecha.getDayOfWeek();
            // Usamos nuestro mapa para una traducción segura
            String nombreDia = DIAS_SEMANA_MAP.get(diaDeLaSemana);

            if (nombreDia == null) {
                // Esto no debería pasar, pero es una buena práctica de defensa
                out.print(gson.toJson(new ArrayList<>()));
                out.flush();
                return;
            }
            
            Optional<Horario> horarioDelDia = horariosSemanales.stream()
                // La comparación ahora es directa, sin ignoreCase
                .filter(h -> h.getDias_semana().equals(nombreDia))
                .findFirst();

            List<String> slotsDisponibles = new ArrayList<>();

            if (horarioDelDia.isPresent()) {
                Horario horario = horarioDelDia.get();
                LocalTime horaInicio = horario.getHora_inicio().toLocalTime();
                LocalTime horaFin = horario.getHora_fin().toLocalTime();
                int intervalo = horario.getIntervalo_citas();

                // 3. Obtener las horas ya ocupadas por otras citas
                List<LocalTime> horasOcupadas = citaDao.getHorasOcupadas(doctorId, Date.valueOf(fecha));

                // 4. Generar todos los slots posibles y filtrar los que no estén ocupados
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
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("Error interno del servidor: " + e.getMessage()));
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
} 
package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@WebServlet({"/recepcionista/nueva-cita", "/usuario/nueva-cita", "/usuario/citas"})
public class RecepcionistaNuevaCitaServlet extends HttpServlet {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final DoctorDao doctorDao = new DoctorDao();
    private final CitasDao citasDao = new CitasDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        Usuario usuarioSesion = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
        String rol = (usuarioSesion != null) ? usuarioSesion.getRol() : null;
        try {
            String servletPath = request.getServletPath();
            if (rol != null && rol.trim().equalsIgnoreCase("paciente")) {
                if (servletPath.equals("/usuario/citas")) {
                    // Mostrar solo las citas del paciente
                    List<Cita> citas = citasDao.getCitasByPacienteId(usuarioSesion.getId());
                    request.setAttribute("citas", citas);
                    request.getRequestDispatcher("/usuario/citas.jsp").forward(request, response);
                    return;
                } else if (servletPath.equals("/usuario/nueva-cita")) {
                    // Formulario de nueva cita para paciente
                    List<Doctor> doctores = doctorDao.getAllDoctores();
                    request.setAttribute("doctores", doctores);
                    request.getRequestDispatcher("/usuario/nueva_cita.jsp").forward(request, response);
                    return;
                }
            }
            List<Usuario> pacientes = usuarioDao.getUsuariosByRol("paciente");
            List<Doctor> doctores = doctorDao.getAllDoctores();
            
            request.setAttribute("pacientes", pacientes);
            request.setAttribute("doctores", doctores);
            
            request.getRequestDispatcher("/recepcionista/nueva_cita.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al cargar datos para nueva cita", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Cita nuevaCita = new Cita();
            jakarta.servlet.http.HttpSession session = request.getSession(false);
            Usuario usuarioSesion = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
            String rol = (usuarioSesion != null) ? usuarioSesion.getRol() : null;
            Integer pacienteId = null;
            if (rol != null && rol.trim().equalsIgnoreCase("paciente")) {
                pacienteId = usuarioSesion.getId();
            } else {
                pacienteId = Integer.parseInt(request.getParameter("paciente_id"));
            }
            // Log de depuración
            System.out.println("[DEBUG] Intentando registrar cita para pacienteId: " + pacienteId);
            System.out.println("[DEBUG] doctorId: " + request.getParameter("doctor_id"));
            System.out.println("[DEBUG] fecha: " + request.getParameter("fecha"));
            System.out.println("[DEBUG] hora: " + request.getParameter("hora"));
            System.out.println("[DEBUG] tipo_consulta: " + request.getParameter("tipo_consulta"));
            System.out.println("[DEBUG] motivo: " + request.getParameter("motivo"));
            System.out.println("[DEBUG] sintomas: " + request.getParameter("sintomas"));
            if (pacienteId == null || pacienteId <= 0) {
                System.out.println("[ERROR] pacienteId nulo o inválido. No se puede registrar la cita.");
                request.setAttribute("error", "No se pudo registrar la cita. Usuario no válido.");
                doGet(request, response);
                return;
            }
            nuevaCita.setPacienteId(pacienteId);
            nuevaCita.setDoctorId(Integer.parseInt(request.getParameter("doctor_id")));
            nuevaCita.setFecha(Date.valueOf(request.getParameter("fecha")));
            
            // Se añaden segundos al formato de la hora para que sea compatible con SQL Time
            String horaParam = request.getParameter("hora");
            if (horaParam.length() == 5) { // Formato HH:mm
                horaParam += ":00";
            }
            nuevaCita.setHora(Time.valueOf(horaParam));
            
            nuevaCita.setTipo_consulta(request.getParameter("tipo_consulta"));
            nuevaCita.setMotivo(request.getParameter("motivo"));
            String sintomas = request.getParameter("sintomas");
            if (sintomas == null) sintomas = "";
            nuevaCita.setSintomas(sintomas);
            nuevaCita.setEstado("pendiente"); // Las citas nuevas siempre están pendientes
            // Log antes del insert
            System.out.println("[DEBUG] Insertando cita en la base de datos...");
            citasDao.insertarCitaCompleta(nuevaCita);
            System.out.println("[DEBUG] Cita insertada correctamente");

            // Redirigir según el rol
            if (rol != null && rol.trim().equalsIgnoreCase("paciente")) {
                response.sendRedirect(request.getContextPath() + "/usuario/citas");
            } else {
                request.getSession().setAttribute("successMessage", "¡Cita registrada exitosamente!");
                response.sendRedirect(request.getContextPath() + "/recepcionista/citas");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos al registrar la cita: " + e.getMessage());
            doGet(request, response); // Recargar el formulario con el mensaje de error
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado al procesar el formulario: " + e.getMessage());
            doGet(request, response); // Recargar el formulario con el mensaje de error
        }
    }
} 
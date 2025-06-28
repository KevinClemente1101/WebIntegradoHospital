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

@WebServlet("/recepcionista/citas")
public class RecepcionistaCitasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !"recepcionista".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            CitasDao citasDao = new CitasDao();
            DoctorDao doctorDao = new DoctorDao();

            // Obtener parámetros de filtro
            String fecha = request.getParameter("fecha");
            String doctorId = request.getParameter("doctor_id");
            String estado = request.getParameter("estado");

            // Obtener citas filtradas
            List<Cita> citas = citasDao.getCitasFiltradas(fecha, doctorId, estado);

            // Obtener lista de doctores para el filtro
            List<Doctor> doctores = doctorDao.getAllDoctores();

            // Establecer atributos
            request.setAttribute("citas", citas);
            request.setAttribute("doctores", doctores);
            request.setAttribute("usuario", usuario);

            request.getRequestDispatcher("/recepcionista/citas.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al cargar las citas", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !"recepcionista".equals(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        try {
            CitasDao citasDao = new CitasDao();

            if ("confirmar".equals(action)) {
                int citaId = Integer.parseInt(request.getParameter("cita_id"));
                citasDao.actualizarEstadoCita(citaId, "confirmada");
                request.setAttribute("successMessage", "Cita confirmada exitosamente");

            } else if ("cancelar".equals(action)) {
                int citaId = Integer.parseInt(request.getParameter("cita_id"));
                citasDao.actualizarEstadoCita(citaId, "cancelada");
                request.setAttribute("successMessage", "Cita cancelada exitosamente");
            }

            // Redirigir de vuelta a la lista de citas
            response.sendRedirect(request.getContextPath() + "/recepcionista/citas");

        } catch (SQLException e) {
            throw new ServletException("Error al procesar la acción", e);
        }
    }
}
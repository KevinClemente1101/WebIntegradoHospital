package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.EspecialidadDao;
import com.mycompany.hospital_citas.Cita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DoctorDao doctorDao = new DoctorDao();
        CitasDao citasDao = new CitasDao();
        EspecialidadDao especialidadDao = new EspecialidadDao();

        try {
            int medicosCount = doctorDao.countDoctores();
            int citasCount = citasDao.countCitas();
            int especialidadesCount = especialidadDao.countEspecialidades();
            List<Cita> ultimasCitas = citasDao.getUltimasCitas(5);

            request.setAttribute("medicosCount", medicosCount);
            request.setAttribute("citasCount", citasCount);
            request.setAttribute("especialidadesCount", especialidadesCount);
            request.setAttribute("ultimasCitas", ultimasCitas);

            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar el dashboard", e);
        }
    }
}
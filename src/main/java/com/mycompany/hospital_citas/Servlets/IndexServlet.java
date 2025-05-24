package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.EspecialidadDao;
import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EspecialidadDao especialidadDao = new EspecialidadDao();
            List<Especialidad> especialidades = especialidadDao.getAllEspecialidades();
            request.setAttribute("especialidades", especialidades);

            DoctorDao doctorDao = new DoctorDao();
            List<Doctor> doctores = doctorDao.getAllDoctores();
            request.setAttribute("doctores", doctores);

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar las especialidades y doctores", e);
        }
    }
} 
package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.dto.EspecialidadDTO;
import com.mycompany.hospital_citas.dao.EspecialidadDao;
import com.mycompany.hospital_citas.dto.DoctorDTO;
import com.mycompany.hospital_citas.dao.DoctorDao;
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
            List<EspecialidadDTO> especialidades = especialidadDao.getAllEspecialidades();
            request.setAttribute("especialidades", especialidades);

            DoctorDao doctorDao = new DoctorDao();
            List<DoctorDTO> doctores = doctorDao.getAllDoctores();
            request.setAttribute("doctores", doctores);

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al cargar las especialidades y doctores", e);
        }
    }
} 
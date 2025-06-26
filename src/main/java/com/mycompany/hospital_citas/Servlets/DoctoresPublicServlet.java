package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DoctoresPublicServlet", urlPatterns = {"/doctores"})
public class DoctoresPublicServlet extends HttpServlet {

    private DoctorDao doctorDao;

    @Override
    public void init() throws ServletException {
        super.init();
        this.doctorDao = new DoctorDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Doctor> doctores = doctorDao.getAllDoctoresConEspecialidadYCorreo();
            request.setAttribute("doctores", doctores);
            request.getRequestDispatcher("/doctores.jsp").forward(request, response);
        } catch (SQLException e) {
            // Manejo de la excepción
            e.printStackTrace(); // Es una buena práctica registrar el error
            throw new ServletException("Error al obtener los doctores de la base de datos", e);
        }
    }
} 
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

@WebServlet("/admin/medicos")
public class MedicosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DoctorDao doctorDao = new DoctorDao();
        try {
            List<Doctor> medicos = doctorDao.getAllDoctoresConEspecialidadYCorreo();
            request.setAttribute("medicos", medicos);
            request.getRequestDispatcher("medicos.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener la lista de médicos", e);
        }
    }
}

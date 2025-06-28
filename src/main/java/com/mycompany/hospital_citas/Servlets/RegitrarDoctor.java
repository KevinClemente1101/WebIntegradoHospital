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

@WebServlet("/registrar-doctor")
public class RegitrarDoctor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener datos del formulario (asumiendo que el usuario ya fue creado y
        // tenemos el usuario_id)
        int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        int especialidadId = Integer.parseInt(request.getParameter("especialidad_id"));
        String biografia = request.getParameter("biografia");

        Doctor doctor = new Doctor();
        doctor.setUsuarioId(usuarioId);
        doctor.setEspecialidadId(especialidadId);
        doctor.setBiografia(biografia);

        DoctorDao doctorDao = new DoctorDao();
        try {
            boolean exito = doctorDao.insertDoctor(doctor);
            if (exito) {
                // Redireccionar a la página de gestión de doctores o a una página de éxito
                response.sendRedirect("admin/gestionarDoctores"); // Example redirect
            } else {
                request.setAttribute("error", "No se pudo registrar el doctor.");
                // Reenviar a una página de error o de vuelta al formulario (si aplica)
                request.getRequestDispatcher("registro_doctor.jsp").forward(request, response); // Example forward
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

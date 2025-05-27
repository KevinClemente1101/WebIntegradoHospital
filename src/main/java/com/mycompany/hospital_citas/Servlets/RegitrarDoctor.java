
package com.mycompany.hospital_citas.Servlets;


import com.mycompany.hospital_citas.dto.DoctorDTO;
import com.mycompany.hospital_citas.dao.DoctorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/doctores")
public class RegitrarDoctor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        int especialidadId = Integer.parseInt(request.getParameter("especialidad_id"));
        String biografia = request.getParameter("biografia");

        DoctorDTO doctor = new DoctorDTO();
        doctor.setUsuarioId(usuarioId);
        doctor.setEspecialidadId(especialidadId);
        doctor.setBiografia(biografia);

        DoctorDao doctorDao = new DoctorDao();
        try {
            boolean exito = doctorDao.insertDoctor(doctor);
            if (exito) {
                response.sendRedirect("doctores.jsp");
            } else {
                request.setAttribute("error", "No se pudo registrar el doctor.");
                request.getRequestDispatcher("registro_doctor.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DoctorDao doctorDao = new DoctorDao();
        try {
            List<DoctorDTO> doctores = doctorDao.getAllDoctores();
            request.setAttribute("doctores", doctores);
            request.getRequestDispatcher("doctores.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
}

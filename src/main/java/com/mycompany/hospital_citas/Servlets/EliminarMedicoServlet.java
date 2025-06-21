package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/eliminarMedico")
public class EliminarMedicoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            DoctorDao doctorDao = new DoctorDao();
            try {
                Doctor doctor = doctorDao.getDoctorById(id);
                int usuarioId = doctor != null ? doctor.getUsuarioId() : -1;
                boolean eliminado = doctorDao.deleteDoctor(id);
                if (eliminado && usuarioId != -1) {
                    UsuarioDao usuarioDao = new UsuarioDao();
                    usuarioDao.deleteUsuario(usuarioId);
                }
                if (eliminado) {
                    request.getSession().setAttribute("successMessage", "Médico y usuario eliminados correctamente.");
                } else {
                    request.getSession().setAttribute("error", "No se pudo eliminar el médico. Puede que tenga registros asociados (citas, horarios, historial, etc).");
                }
            } catch (SQLException e) {
                String mensaje = e.getMessage();
                if (mensaje != null && mensaje.contains("a foreign key constraint fails")) {
                    request.getSession().setAttribute("error", "No se puede eliminar el médico porque tiene registros asociados (citas, horarios, historial, etc).");
                } else {
                    request.getSession().setAttribute("error", "Error al eliminar el médico: " + mensaje);
                }
            }
        }
        response.sendRedirect("admin/medicos");
    }
} 
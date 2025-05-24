package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.EspecialidadDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/especialidades")
public class RegitroEspecialidad extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(nombre);
        especialidad.setDescripcion(descripcion);

        EspecialidadDao especialidadDao = new EspecialidadDao();
        try {
            boolean exito = especialidadDao.insertEspecialidad(especialidad);
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/admin/especialidades");
            } else {
                request.setAttribute("error", "No se pudo registrar la especialidad.");
                request.getRequestDispatcher("/admin/nueva_especialidad.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EspecialidadDao especialidadDao = new EspecialidadDao();
        try {
            List<Especialidad> especialidades = especialidadDao.getAllEspecialidades();
            request.setAttribute("especialidades", especialidades);
            request.getRequestDispatcher("/admin/especialidades.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.EspecialidadDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/eliminarEspecialidad")
public class EliminarEspecialidadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            EspecialidadDao especialidadDao = new EspecialidadDao();
            try {
                boolean eliminado = especialidadDao.deleteEspecialidad(id);
                if (eliminado) {
                    request.getSession().setAttribute("successMessage", "Especialidad eliminada correctamente.");
                } else {
                    request.getSession().setAttribute("error",
                            "No se pudo eliminar la especialidad. Puede que tenga registros asociados.");
                }
            } catch (SQLException e) {
                String mensaje = e.getMessage();
                if (mensaje != null && mensaje.contains("a foreign key constraint fails")) {
                    request.getSession().setAttribute("error",
                            "No se puede eliminar la especialidad porque tiene registros asociados con médicos.");
                } else {
                    request.getSession().setAttribute("error", "Error al eliminar la especialidad: " + mensaje);
                }
            }
        }
        response.sendRedirect("admin/especialidades");
    }
}
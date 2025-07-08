package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Medicamento;
import com.mycompany.hospital_citas.MedicamentoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/medicamentos")
public class AdminMedicamentosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MedicamentoDao dao = new MedicamentoDao();
        try {
            List<Medicamento> medicamentos = dao.getAllMedicamentos();
            request.setAttribute("medicamentos", medicamentos);
            request.getRequestDispatcher("medicamentos.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener medicamentos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        MedicamentoDao dao = new MedicamentoDao();
        String mensaje = null;
        try {
            if (request.getParameter("id") != null && request.getParameter("nombre") != null && request.getParameter("editar") != null) {
                // Editar medicamento
                Medicamento m = new Medicamento();
                m.setId(Integer.parseInt(request.getParameter("id")));
                m.setNombre(request.getParameter("nombre"));
                m.setDescripcion(request.getParameter("descripcion"));
                m.setDosis(request.getParameter("dosis"));
                m.setFrecuencia(request.getParameter("frecuencia"));
                m.setDuracion(request.getParameter("duracion"));
                m.setContraindicaciones(request.getParameter("contraindicaciones"));
                dao.actualizarMedicamento(m);
                mensaje = "Medicamento actualizado correctamente.";
            } else if (request.getParameter("nombre") != null && request.getParameter("agregar") != null) {
                // Agregar medicamento
                Medicamento m = new Medicamento();
                m.setNombre(request.getParameter("nombre"));
                m.setDescripcion(request.getParameter("descripcion"));
                m.setDosis(request.getParameter("dosis"));
                m.setFrecuencia(request.getParameter("frecuencia"));
                m.setDuracion(request.getParameter("duracion"));
                m.setContraindicaciones(request.getParameter("contraindicaciones"));
                dao.insertarMedicamento(m);
                mensaje = "Medicamento agregado correctamente.";
            } else if (request.getParameter("eliminar") != null && request.getParameter("id") != null) {
                // Eliminar medicamento
                int id = Integer.parseInt(request.getParameter("id"));
                dao.eliminarMedicamento(id);
                mensaje = "Medicamento eliminado correctamente.";
            }
        } catch (SQLException e) {
            mensaje = "Error en la operación: " + e.getMessage();
        }
        request.setAttribute("successMessage", mensaje);
        doGet(request, response);
    }
} 
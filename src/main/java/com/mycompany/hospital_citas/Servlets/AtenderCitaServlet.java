package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.DBUtil;
import com.mycompany.hospital_citas.RecetaMedica;
import com.mycompany.hospital_citas.RecetaMedicaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/doctor/AtenderCitaServlet")
public class AtenderCitaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int citaId = Integer.parseInt(request.getParameter("cita_id"));
        String diagnostico = request.getParameter("diagnostico");
        String tratamiento = request.getParameter("tratamiento");
        String observaciones = request.getParameter("observaciones");

        try (Connection conn = DBUtil.getConnection()) {
            // Actualizar la cita con diagnóstico, tratamiento y observaciones
            String updateCita = "UPDATE citas SET diagnostico=?, tratamiento=?, observaciones=?, estado='completada' WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(updateCita)) {
                stmt.setString(1, diagnostico);
                stmt.setString(2, tratamiento);
                stmt.setString(3, observaciones);
                stmt.setInt(4, citaId);
                stmt.executeUpdate();
            }
            // Procesar recetas médicas
            String[] medicamentoIds = request.getParameterValues("medicamento_id[]");
            String[] dosisArr = request.getParameterValues("dosis[]");
            String[] frecuenciaArr = request.getParameterValues("frecuencia[]");
            String[] duracionArr = request.getParameterValues("duracion[]");
            String[] observacionesArr = request.getParameterValues("observaciones_receta[]");
            if (medicamentoIds != null && medicamentoIds.length > 0) {
                List<RecetaMedica> recetas = new ArrayList<>();
                for (int i = 0; i < medicamentoIds.length; i++) {
                    if (medicamentoIds[i] == null || medicamentoIds[i].trim().isEmpty()) continue; // Salta vacíos
                    RecetaMedica recetaMedica = new RecetaMedica();
                    recetaMedica.setMedicamentoId(Integer.parseInt(medicamentoIds[i]));
                    recetaMedica.setDosis(dosisArr[i]);
                    recetaMedica.setFrecuencia(frecuenciaArr[i]);
                    recetaMedica.setDuracion(duracionArr[i]);
                    recetaMedica.setObservaciones(observacionesArr[i]);
                    recetas.add(recetaMedica);
                }
                RecetaMedicaDao recetaDao = new RecetaMedicaDao();
                recetaDao.insertarRecetas(citaId, recetas);
            }
            // Redirigir a la lista de citas del doctor
            response.sendRedirect(request.getContextPath() + "/doctor/citas");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar la atención médica: " + e.getMessage());
            request.getRequestDispatcher("/doctor/atenderCita.jsp").forward(request, response);
        }
    }
} 
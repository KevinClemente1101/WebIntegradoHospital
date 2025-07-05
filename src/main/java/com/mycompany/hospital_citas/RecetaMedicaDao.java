package com.mycompany.hospital_citas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RecetaMedicaDao {
    public void insertarRecetas(int citaId, List<RecetaMedica> recetas) throws SQLException {
        String sql = "INSERT INTO recetas_medicas (cita_id, medicamento_id, dosis, frecuencia, duracion, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection()) {
            for (RecetaMedica receta : recetas) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, citaId);
                    stmt.setInt(2, receta.getMedicamentoId());
                    stmt.setString(3, receta.getDosis());
                    stmt.setString(4, receta.getFrecuencia());
                    stmt.setString(5, receta.getDuracion());
                    stmt.setString(6, receta.getObservaciones());
                    stmt.executeUpdate();
                }
            }
        }
    }
} 
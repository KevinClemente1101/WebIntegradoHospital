package com.mycompany.hospital_citas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDao {
    public List<Medicamento> getAllMedicamentosActivos() throws SQLException {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE estado = 1";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Medicamento m = new Medicamento();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setDosis(rs.getString("dosis"));
                m.setFrecuencia(rs.getString("frecuencia"));
                m.setDuracion(rs.getString("duracion"));
                m.setContraindicaciones(rs.getString("contraindicaciones"));
                m.setEstado(rs.getInt("estado"));
                m.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                medicamentos.add(m);
            }
        }
        return medicamentos;
    }
} 
package com.mycompany.hospital_citas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDao {
    public List<Medicamento> getAllMedicamentos() throws SQLException {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos";
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

    public void insertarMedicamento(Medicamento m) throws SQLException {
        String sql = "INSERT INTO medicamentos (nombre, descripcion, dosis, frecuencia, duracion, contraindicaciones, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, 1, CURRENT_TIMESTAMP)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNombre());
            stmt.setString(2, m.getDescripcion());
            stmt.setString(3, m.getDosis());
            stmt.setString(4, m.getFrecuencia());
            stmt.setString(5, m.getDuracion());
            stmt.setString(6, m.getContraindicaciones());
            stmt.executeUpdate();
        }
    }

    public void actualizarMedicamento(Medicamento m) throws SQLException {
        String sql = "UPDATE medicamentos SET nombre=?, descripcion=?, dosis=?, frecuencia=?, duracion=?, contraindicaciones=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getNombre());
            stmt.setString(2, m.getDescripcion());
            stmt.setString(3, m.getDosis());
            stmt.setString(4, m.getFrecuencia());
            stmt.setString(5, m.getDuracion());
            stmt.setString(6, m.getContraindicaciones());
            stmt.setInt(7, m.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarMedicamento(int id) throws SQLException {
        String sql = "DELETE FROM medicamentos WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Medicamento getMedicamentoById(int id) throws SQLException {
        String sql = "SELECT * FROM medicamentos WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
                    return m;
                }
            }
        }
        return null;
    }
} 
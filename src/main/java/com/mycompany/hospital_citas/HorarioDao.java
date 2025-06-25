package com.mycompany.hospital_citas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Time;

public class HorarioDao {
    
    public List<Horario> getAllHorariosConMedico() throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT h.*, u.nombre AS medicoNombre " +
                     "FROM horarios h " +
                     "JOIN medicos d ON h.doctor_id = d.id " +
                     "JOIN usuarios u ON d.usuario_id = u.id " +
                     "WHERE h.estado = 1";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setDoctor_id(rs.getInt("doctor_id"));
                horario.setFecha_inicio(rs.getDate("fecha_inicio"));
                horario.setFecha_fin(rs.getDate("fecha_fin"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horarios.add(horario);
            }
        }
        return horarios;
    }

    public List<Horario> getHorariosByDoctorId(int doctorId) throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE doctor_id = ? AND estado = 1 ORDER BY fecha_inicio, hora_inicio";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setDoctor_id(rs.getInt("doctor_id"));
                horario.setFecha_inicio(rs.getDate("fecha_inicio"));
                horario.setFecha_fin(rs.getDate("fecha_fin"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horario.setIntervalo_citas(rs.getInt("intervalo_citas"));
                horarios.add(horario);
            }
        }
        return horarios;
    }
    
    public void insertHorario(Horario horario) throws SQLException {
        String sql = "INSERT INTO horarios (doctor_id, fecha_inicio, fecha_fin, hora_inicio, hora_fin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, horario.getDoctor_id());
            stmt.setDate(2, horario.getFecha_inicio());
            stmt.setDate(3, horario.getFecha_fin());
            stmt.setTime(4, horario.getHora_inicio());
            stmt.setTime(5, horario.getHora_fin());
            stmt.executeUpdate();
        }
    }
    
    public void deleteHorario(int horarioId) throws SQLException {
        String sql = "DELETE FROM horarios WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, horarioId);
            stmt.executeUpdate();
        }
    }

    public boolean verificarTraslape(int doctorId, java.sql.Date fechaInicio, java.sql.Date fechaFin, Time horaInicio, Time horaFin) throws SQLException {
        String sql = "SELECT COUNT(*) FROM horarios WHERE doctor_id = ? " +
                     "AND ((fecha_inicio <= ? AND fecha_fin >= ?) OR (fecha_inicio <= ? AND fecha_fin >= ?)) " +
                     "AND ((hora_inicio < ? AND hora_fin > ?) OR (hora_inicio >= ? AND hora_inicio < ?) OR (hora_fin > ? AND hora_fin <= ?))";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setDate(2, fechaFin);
            stmt.setDate(3, fechaInicio);
            stmt.setDate(4, fechaInicio);
            stmt.setDate(5, fechaFin);
            stmt.setTime(6, horaFin);
            stmt.setTime(7, horaInicio);
            stmt.setTime(8, horaInicio);
            stmt.setTime(9, horaFin);
            stmt.setTime(10, horaInicio);
            stmt.setTime(11, horaFin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}

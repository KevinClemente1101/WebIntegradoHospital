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
                horario.setDias_semana(rs.getString("dias_semana"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horarios.add(horario);
            }
        }
        return horarios;
    }

    public List<Horario> getHorariosByDoctorId(int doctorId) throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE doctor_id = ? AND estado = 1 ORDER BY FIELD(dias_semana, 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'), hora_inicio";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setDoctor_id(rs.getInt("doctor_id"));
                horario.setDias_semana(rs.getString("dias_semana"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horario.setIntervalo_citas(rs.getInt("intervalo_citas"));
                horarios.add(horario);
            }
        }
        return horarios;
    }
    
    public void insertHorario(Horario horario) throws SQLException {
        String sql = "INSERT INTO horarios (doctor_id, dias_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, horario.getDoctor_id());
            stmt.setString(2, horario.getDias_semana());
            stmt.setTime(3, horario.getHora_inicio());
            stmt.setTime(4, horario.getHora_fin());
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

    public boolean verificarTraslape(int doctorId, String diaSemana, Time horaInicio, Time horaFin) throws SQLException {
        String sql = "SELECT COUNT(*) FROM horarios WHERE doctor_id = ? AND dias_semana = ? AND (" +
                     "    (hora_inicio < ? AND hora_fin > ?) OR " +
                     "    (hora_inicio >= ? AND hora_inicio < ?) OR " +
                     "    (hora_fin > ? AND hora_fin <= ?)" +
                     ")";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, diaSemana);
            stmt.setTime(3, horaFin);
            stmt.setTime(4, horaInicio);
            stmt.setTime(5, horaInicio);
            stmt.setTime(6, horaFin);
            stmt.setTime(7, horaInicio);
            stmt.setTime(8, horaFin);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}

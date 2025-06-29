package com.mycompany.hospital_citas;

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
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horarios.add(horario);
            }
        }
        return horarios;
    }

    public List<Horario> getHorariosByDoctorId(int doctorId) throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE doctor_id = ? AND estado = 1 ORDER BY hora_inicio";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setDoctor_id(rs.getInt("doctor_id"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horarios.add(horario);
            }
        }
        return horarios;
    }

    public List<Horario> getHorariosByDoctorIdAndDay(int doctorId, String diaSemana) throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE doctor_id = ? AND estado = 1 " +
                "AND fecha_inicio <= CURDATE() AND fecha_fin >= CURDATE() " +
                "ORDER BY hora_inicio";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Horario horario = new Horario();
                horario.setId(rs.getInt("id"));
                horario.setDoctor_id(rs.getInt("doctor_id"));
                horario.setHora_inicio(rs.getTime("hora_inicio"));
                horario.setHora_fin(rs.getTime("hora_fin"));
                horario.setDiaSemana(rs.getString("dia_semana"));
                horario.setEstado(rs.getBoolean("estado"));
                horarios.add(horario);
            }
        }
        return horarios;
    }

    public void insertHorario(Horario horario) throws SQLException {
        String sql = "INSERT INTO horarios (doctor_id, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, horario.getDoctor_id());
            stmt.setString(2, horario.getDiaSemana());
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

    public boolean verificarTraslape(int doctorId, String diaSemana, Time horaInicio, Time horaFin)
            throws SQLException {
        String sql = "SELECT COUNT(*) FROM horarios WHERE doctor_id = ? AND dia_semana = ? AND estado = 1 " +
                "AND ((hora_inicio < ? AND hora_fin > ?) " + // Caso 1: Nuevo horario dentro de uno existente
                "OR (hora_inicio >= ? AND hora_inicio < ?) " + // Caso 2: Inicio dentro de horario existente
                "OR (hora_fin > ? AND hora_fin <= ?) " + // Caso 3: Fin dentro de horario existente
                "OR (hora_inicio <= ? AND hora_fin >= ?))"; // Caso 4: Horario existente dentro del nuevo

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setString(2, diaSemana);

            // Parámetros para los casos
            stmt.setTime(3, horaFin); // Para Caso 1: hora_fin > ?
            stmt.setTime(4, horaInicio); // Para Caso 1: hora_inicio < ?

            stmt.setTime(5, horaInicio); // Para Caso 2: hora_inicio >= ?
            stmt.setTime(6, horaFin); // Para Caso 2: hora_inicio < ?

            stmt.setTime(7, horaInicio); // Para Caso 3: hora_fin > ?
            stmt.setTime(8, horaFin); // Para Caso 3: hora_fin <= ?

            stmt.setTime(9, horaInicio); // Para Caso 4: hora_inicio <= ?
            stmt.setTime(10, horaFin); // Para Caso 4: hora_fin >= ?

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}

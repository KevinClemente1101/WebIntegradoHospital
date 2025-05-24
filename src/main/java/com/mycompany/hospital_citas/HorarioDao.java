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
                horario.setMedicoNombre(rs.getString("medicoNombre"));
                horario.setDia(rs.getString("dia_semana"));
                horario.setHoraInicio(rs.getString("hora_inicio"));
                horario.setHoraFin(rs.getString("hora_fin"));
                // Agrega otros campos según tu clase Horario
                horarios.add(horario);
            }
        }
        return horarios;
    }
}

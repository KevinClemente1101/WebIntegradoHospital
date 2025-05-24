package com.mycompany.hospital_citas;


import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.DBUtil;
import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;
public class CitasDao {
      public Cita getCitaById(int id) throws SQLException {
        Cita cita = null;
        String sql = "SELECT * FROM citas WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setUsuarioId(rs.getInt("usuario_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                cita.setEstado(rs.getString("estado"));
            }
        }
        return cita;
    }

    public List<Cita> getCitasByUsuario(int usuarioId) throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE usuario_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setUsuarioId(rs.getInt("usuario_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                cita.setEstado(rs.getString("estado"));
                lista.add(cita);
            }
        }
        return lista;
    }

    public List<Cita> getAllCitas() throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setUsuarioId(rs.getInt("usuario_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                cita.setEstado(rs.getString("estado"));
                lista.add(cita);
            }
        }
        return lista;
    }

    public boolean insertCita(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (usuario_id, doctor_id, fecha_hora, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getUsuarioId());
            stmt.setInt(2, cita.getDoctorId());
            stmt.setTimestamp(3, cita.getFechaHora());
            stmt.setString(4, cita.getEstado());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateCita(Cita cita) throws SQLException {
        String sql = "UPDATE citas SET usuario_id = ?, doctor_id = ?, fecha_hora = ?, estado = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getUsuarioId());
            stmt.setInt(2, cita.getDoctorId());
            stmt.setTimestamp(3, cita.getFechaHora());
            stmt.setString(4, cita.getEstado());
            stmt.setInt(5, cita.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCita(int id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public int countCitas() throws SQLException {
        String sql = "SELECT COUNT(*) FROM citas";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<Cita> getUltimasCitas(int limite) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, up.nombre AS pacienteNombre, ud.nombre AS doctorNombre " +
                     "FROM citas c " +
                     "JOIN usuarios up ON c.paciente_id=up.id " +
                     "JOIN medicos d ON c.doctor_id=d.id " +
                     "JOIN usuarios ud ON d.usuario_id=ud.id " +
                     "ORDER BY c.fecha_creacion DESC LIMIT ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteNombre(rs.getString("pacienteNombre"));
                cita.setDoctorNombre(rs.getString("doctorNombre"));
                Date fecha = rs.getDate("fecha");
                Time hora = rs.getTime("hora");
                if (fecha != null && hora != null) {
                    cita.setFechaHora(new Timestamp(fecha.getTime() + hora.getTime()));
                } else {
                    cita.setFechaHora(null);
                }
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
    }

    public List<Cita> getAllCitasConNombres() throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, up.nombre AS pacienteNombre, ud.nombre AS doctorNombre " +
                     "FROM citas c " +
                     "JOIN usuarios up ON c.paciente_id=up.id " +
                     "JOIN medicos d ON c.doctor_id=d.id " +
                     "JOIN usuarios ud ON d.usuario_id=ud.id";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteNombre(rs.getString("pacienteNombre"));
                cita.setDoctorNombre(rs.getString("doctorNombre"));
                Date fecha = rs.getDate("fecha");
                Time hora = rs.getTime("hora");
                if (fecha != null && hora != null) {
                    cita.setFechaHora(new Timestamp(fecha.getTime() + hora.getTime()));
                } else {
                    cita.setFechaHora(null);
                }
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
    }
}

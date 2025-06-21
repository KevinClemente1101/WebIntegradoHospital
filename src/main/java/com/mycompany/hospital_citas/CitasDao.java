package com.mycompany.hospital_citas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalTime;

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
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
                cita.setTipo_consulta(rs.getString("tipo_consulta"));
                cita.setMotivo(rs.getString("motivo"));
                cita.setSintomas(rs.getString("sintomas"));
            }
        }
        return cita;
    }

    public List<Cita> getCitasByPacienteId(int pacienteId) throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE paciente_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
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
                cita.setPacienteId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
                lista.add(cita);
            }
        }
        return lista;
    }

    public void insertarCitaCompleta(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (paciente_id, doctor_id, fecha, hora, estado, tipo_consulta, motivo, sintomas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getPacienteId());
            stmt.setInt(2, cita.getDoctorId());
            stmt.setDate(3, cita.getFecha());
            stmt.setTime(4, cita.getHora());
            stmt.setString(5, cita.getEstado());
            stmt.setString(6, cita.getTipo_consulta());
            stmt.setString(7, cita.getMotivo());
            stmt.setString(8, cita.getSintomas());
            stmt.executeUpdate();
        }
    }

    public boolean updateCita(Cita cita) throws SQLException {
        String sql = "UPDATE citas SET paciente_id = ?, doctor_id = ?, fecha = ?, hora = ?, estado = ?, tipo_consulta = ?, motivo = ?, sintomas = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getPacienteId());
            stmt.setInt(2, cita.getDoctorId());
            stmt.setDate(3, cita.getFecha());
            stmt.setTime(4, cita.getHora());
            stmt.setString(5, cita.getEstado());
            stmt.setString(6, cita.getTipo_consulta());
            stmt.setString(7, cita.getMotivo());
            stmt.setString(8, cita.getSintomas());
            stmt.setInt(9, cita.getId());
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
    
    public int getCitasByDate(java.time.LocalDate date) throws SQLException {
        String sql = "SELECT COUNT(*) FROM citas WHERE fecha = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public int getCitasByEstado(String estado) throws SQLException {
        String sql = "SELECT COUNT(*) FROM citas WHERE estado = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
    
    public List<Cita> getProximasCitas(int days) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido, " +
                     "d.id AS doctor_id, u.nombre AS doctor_nombre, u.apellido AS doctor_apellido, " +
                     "e.nombre AS especialidad_nombre " +
                     "FROM citas c " +
                     "JOIN usuarios p ON c.paciente_id = p.id " +
                     "JOIN medicos d ON c.doctor_id = d.id " +
                     "JOIN usuarios u ON d.usuario_id = u.id " +
                     "JOIN especialidades e ON d.especialidad_id = e.id " +
                     "WHERE c.fecha BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) " +
                     "ORDER BY c.fecha, c.hora " +
                     "LIMIT 10";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, days);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                
                Usuario paciente = new Usuario();
                paciente.setNombre(rs.getString("paciente_nombre"));
                paciente.setApellido(rs.getString("paciente_apellido"));
                cita.setPaciente(paciente);
                
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("doctor_id"));
                Usuario usuarioDoctor = new Usuario();
                usuarioDoctor.setNombre(rs.getString("doctor_nombre"));
                usuarioDoctor.setApellido(rs.getString("doctor_apellido"));
                doctor.setUsuario(usuarioDoctor);
                
                Especialidad especialidad = new Especialidad();
                especialidad.setNombre(rs.getString("especialidad_nombre"));
                doctor.setEspecialidad(especialidad);
                
                cita.setDoctor(doctor);
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
    }
    
    public List<Cita> getCitasFiltradas(String fecha, String doctorId, String estado) throws SQLException {
        List<Cita> citas = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT c.*, p.nombre AS paciente_nombre, p.apellido AS paciente_apellido, " +
            "d.id AS doctor_id, u.nombre AS doctor_nombre, u.apellido AS doctor_apellido, " +
            "e.nombre AS especialidad_nombre " +
            "FROM citas c " +
            "JOIN usuarios p ON c.paciente_id = p.id " +
            "JOIN medicos d ON c.doctor_id = d.id " +
            "JOIN usuarios u ON d.usuario_id = u.id " +
            "JOIN especialidades e ON d.especialidad_id = e.id "
        );

        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (fecha != null && !fecha.isEmpty()) {
            sql.append(" WHERE c.fecha = ?");
            params.add(java.sql.Date.valueOf(fecha));
            hasWhere = true;
        }

        if (doctorId != null && !doctorId.isEmpty()) {
            sql.append(hasWhere ? " AND" : " WHERE").append(" c.doctor_id = ?");
            params.add(Integer.parseInt(doctorId));
            hasWhere = true;
        }

        if (estado != null && !estado.isEmpty()) {
            sql.append(hasWhere ? " AND" : " WHERE").append(" c.estado = ?");
            params.add(estado);
        }
        
        sql.append(" ORDER BY c.fecha, c.hora");

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                
                Usuario paciente = new Usuario();
                paciente.setNombre(rs.getString("paciente_nombre"));
                paciente.setApellido(rs.getString("paciente_apellido"));
                cita.setPaciente(paciente);
                
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("doctor_id"));
                Usuario usuarioDoctor = new Usuario();
                usuarioDoctor.setNombre(rs.getString("doctor_nombre"));
                usuarioDoctor.setApellido(rs.getString("doctor_apellido"));
                doctor.setUsuario(usuarioDoctor);
                
                Especialidad especialidad = new Especialidad();
                especialidad.setNombre(rs.getString("especialidad_nombre"));
                doctor.setEspecialidad(especialidad);
                
                cita.setDoctor(doctor);
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
                cita.setTipo_consulta(rs.getString("tipo_consulta"));
                citas.add(cita);
            }
        }
        return citas;
    }
    
    public void actualizarEstadoCita(int citaId, String nuevoEstado) throws SQLException {
        String sql = "UPDATE citas SET estado = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, citaId);
            stmt.executeUpdate();
        }
    }

    public List<Cita> getAllCitasConNombres() throws SQLException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, up.nombre AS pacienteNombre, ud.nombre AS doctorNombre " +
                     "FROM citas c " +
                     "JOIN usuarios up ON c.paciente_id = up.id " +
                     "JOIN medicos d ON c.doctor_id = d.id " +
                     "JOIN usuarios ud ON d.usuario_id = ud.id";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setPacienteNombre(rs.getString("pacienteNombre"));
                cita.setDoctorNombre(rs.getString("doctorNombre"));
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
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
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
                citas.add(cita);
            }
        }
        return citas;
    }

    public List<LocalTime> getHorasOcupadas(int doctorId, Date fecha) throws SQLException {
        List<LocalTime> horasOcupadas = new ArrayList<>();
        String sql = "SELECT hora FROM citas WHERE doctor_id = ? AND fecha = ? AND estado_cita != 'Cancelada'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, fecha);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                horasOcupadas.add(rs.getTime("hora").toLocalTime());
            }
        }
        return horasOcupadas;
    }
}

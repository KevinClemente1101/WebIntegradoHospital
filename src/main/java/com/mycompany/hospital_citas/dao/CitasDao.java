package com.mycompany.hospital_citas.dao;

import com.mycompany.hospital_citas.dto.Cita;
import com.mycompany.hospital_citas.dto.HorarioDTO;
import com.mycompany.hospital_citas.dto.MedicoDTO;
import com.mycompany.hospital_citas.util.DBUtil;
import com.mycompany.hospital_citas.dto.CitaDTO;
import com.mycompany.hospital_citas.dto.CitaDTO;
import com.mycompany.hospital_citas.dto.EspecialidadDTO;
import com.mycompany.hospital_citas.dto.MedicoDTO;
import java.sql.*;
import java.util.*;
public class CitasDao {
    
    /**
    * Obtener Cita por ID.
    */
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
                cita.setUsuarioId(rs.getInt("paciente_id"));
                cita.setDoctorId(rs.getInt("doctor_id"));
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEstado(rs.getString("estado"));
            }
        }
        return cita;
    }

    /**
    * Obtener citas de un usuario.
    */
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
                // cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                cita.setEstado(rs.getString("estado"));
                lista.add(cita);
            }
        }
        return lista;
    }

    /**
    * Obtener todas las citas.
    */
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
                // cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                cita.setEstado(rs.getString("estado"));
                lista.add(cita);
            }
        }
        return lista;
    }

    /**
    * Agregar nueva cita.
    */
    public boolean insertCita(Cita cita) throws SQLException {
        String sql = "INSERT INTO citas (usuario_id, doctor_id, fecha_hora, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getUsuarioId());
            stmt.setInt(2, cita.getDoctorId());
            // stmt.setTimestamp(3, cita.getFechaHora());
            stmt.setString(4, cita.getEstado());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
    * Editar una cita.
    */
    public boolean updateCita(Cita cita) throws SQLException {
        String sql = "UPDATE citas SET paciente_id = ?, doctor_id = ?, fecha = ?, hora = ?, estado = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getUsuarioId());
            stmt.setInt(2, cita.getDoctorId());
            stmt.setDate(3, cita.getFecha());
            stmt.setTime(4, cita.getHora());
            stmt.setString(5, cita.getEstado());
            stmt.setInt(6, cita.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
    * Eliminar una cita.
    */
    public boolean deleteCita(int id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
    * Trae las citas futuras de un paciente, con JOIN para médico y especialidad.
    */
    public List<CitaDTO> getUpcomingCitasByUsuario(int usuarioId) throws SQLException {
        String sql = """
            SELECT c.id,
                   c.fecha          AS fecha,
                   c.hora           AS hora,
                   c.estado         AS estado,
                   u.nombre         AS medicoNombre,
                   e.nombre         AS especialidad
            FROM citas c
            JOIN medicos m     ON c.doctor_id = m.id
            JOIN usuarios u    ON m.usuario_id = u.id
            JOIN especialidades e ON m.especialidad_id = e.id
            WHERE c.paciente_id = ?
              AND c.fecha >= CURDATE()
            ORDER BY c.fecha, c.hora
            """;

        List<CitaDTO> lista = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CitaDTO dto = new CitaDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setFecha(rs.getDate("fecha"));
                    dto.setHora(rs.getTime("hora"));
                    dto.setEstado(rs.getString("estado"));
                    dto.setMedicoNombre(rs.getString("medicoNombre"));
                    dto.setEspecialidad(rs.getString("especialidad"));
                    lista.add(dto);
                }
            }
        }
        return lista;
    }
    
    /**
    * Cuenta las citas de un paciente por estado.
    */
    public int countCitasByEstado(int pacienteId, String estado) throws SQLException {
        String sql = "SELECT COUNT(*) FROM citas WHERE paciente_id = ? AND estado = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteId);
            ps.setString(2, estado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    /**
    * Obtener todaslas citas del usuario mas datos.
    */
    public List<CitaDTO> getHistorialCitasByUsuario(int usuarioId) throws SQLException {
    String sql = """
        SELECT c.id,
               c.fecha          AS fecha,
               c.hora           AS hora,
               c.estado         AS estado,
               u.nombre         AS medicoNombre,
               e.nombre         AS especialidad
        FROM citas c
        JOIN medicos m     ON c.doctor_id = m.id
        JOIN usuarios u    ON m.usuario_id = u.id
        JOIN especialidades e ON m.especialidad_id = e.id
        WHERE c.paciente_id = ?
        ORDER BY c.fecha DESC, c.hora DESC
        """;

        List<CitaDTO> lista = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CitaDTO dto = new CitaDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setFecha(rs.getDate("fecha"));
                    dto.setHora(rs.getTime("hora"));
                    dto.setEstado(rs.getString("estado"));
                    dto.setMedicoNombre(rs.getString("medicoNombre"));
                    dto.setEspecialidad(rs.getString("especialidad"));
                    lista.add(dto);
                }
            }
        }
        return lista;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
    * Trae todas las especialidades.
    */
   public List<EspecialidadDTO> getAllEspecialidades() throws SQLException {
       String sql = "SELECT id, nombre FROM especialidades ORDER BY nombre";
       List<EspecialidadDTO> lista = new ArrayList<>();
       try (Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
           while (rs.next()) {
               lista.add(new EspecialidadDTO(rs.getInt("id"), rs.getString("nombre")));
           }
       }
       return lista;
   }

   /**
    * Médicos de una especialidad.
    */
   public List<MedicoDTO> getMedicosByEspecialidad(int espId) throws SQLException {
       String sql = "SELECT m.id, u.nombre " +
                    "FROM medicos m JOIN usuarios u ON m.usuario_id=u.id " +
                    "WHERE m.especialidad_id=? ORDER BY u.nombre";
       List<MedicoDTO> lista = new ArrayList<>();
       try (Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
           ps.setInt(1, espId);
           try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                   lista.add(new MedicoDTO(rs.getInt("id"), rs.getString("nombre")));
               }
           }
       }
       return lista;
   }

   /**
    * Horarios de un médico.
    */
   public List<HorarioDTO> getHorariosByMedico(int medicoId) throws SQLException {
       String sql = "SELECT id, dia_semana, hora_inicio FROM horarios WHERE doctor_id=? ORDER BY dia_semana, hora_inicio";
       List<HorarioDTO> lista = new ArrayList<>();
       try (Connection c = DBUtil.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
           ps.setInt(1, medicoId);
           try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                   lista.add(new HorarioDTO(
                       rs.getInt("id"),
                       rs.getString("dia_semana"),
                       rs.getTime("hora_inicio")
                   ));
               }
           }
       }
       return lista;
   }

}

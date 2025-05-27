package com.mycompany.hospital_citas.dao;


import com.mycompany.hospital_citas.dto.DoctorDTO;
import com.mycompany.hospital_citas.dto.EspecialidadDTO;
import com.mycompany.hospital_citas.dto.UsuarioDTO;
import com.mycompany.hospital_citas.dto.UsuarioDTO;
import com.mycompany.hospital_citas.util.DBUtil;
import java.sql.*;
import java.util.*;
public class DoctorDao {
       public DoctorDTO getDoctorById(int id) throws SQLException {
        DoctorDTO doctor = null;
        String sql = "SELECT * FROM medicos WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                doctor = new DoctorDTO();
                doctor.setId(rs.getInt("id"));
                doctor.setUsuarioId(rs.getInt("usuario_id"));
                doctor.setEspecialidadId(rs.getInt("especialidad_id"));
                doctor.setBiografia(rs.getString("biografia"));
            }
        }
        return doctor;
    }

    public List<DoctorDTO> getAllDoctores() throws SQLException {
        List<DoctorDTO> doctores = new ArrayList<>();
        String sql = "SELECT m.*, u.nombre, u.apellido, u.foto_perfil, e.nombre as especialidad_nombre " +
                     "FROM medicos m " +
                     "JOIN usuarios u ON m.usuario_id = u.id " +
                     "JOIN especialidades e ON m.especialidad_id = e.id";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DoctorDTO doctor = new DoctorDTO();
                doctor.setId(rs.getInt("id"));
                doctor.setUsuarioId(rs.getInt("usuario_id"));
                doctor.setEspecialidadId(rs.getInt("especialidad_id"));
                doctor.setBiografia(rs.getString("biografia"));
                
                // Crear y establecer el usuario
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFoto(rs.getString("foto_perfil"));
                doctor.setUsuario(usuario);
                
                // Crear y establecer la especialidad
                EspecialidadDTO especialidad = new EspecialidadDTO();
                especialidad.setId(rs.getInt("especialidad_id"));
                especialidad.setNombre(rs.getString("especialidad_nombre"));
                doctor.setEspecialidad(especialidad);
                
                doctores.add(doctor);
            }
        }
        return doctores;
    }

    public boolean insertDoctor(DoctorDTO doctor) throws SQLException {
        String sql = "INSERT INTO medicos (usuario_id, especialidad_id, biografia) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctor.getUsuarioId());
            stmt.setInt(2, doctor.getEspecialidadId());
            stmt.setString(3, doctor.getBiografia());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateDoctor(DoctorDTO doctor) throws SQLException {
        String sql = "UPDATE medicos SET usuario_id = ?, especialidad_id = ?, biografia = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctor.getUsuarioId());
            stmt.setInt(2, doctor.getEspecialidadId());
            stmt.setString(3, doctor.getBiografia());
            stmt.setInt(4, doctor.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDoctor(int id) throws SQLException {
        String sql = "DELETE FROM medicos WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}

package com.mycompany.hospital_citas;


import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DBUtil;
import java.sql.*;
import java.util.*;
public class DoctorDao {
       public Doctor getDoctorById(int id) throws SQLException {
        Doctor doctor = null;
        String sql = "SELECT * FROM medicos WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setUsuarioId(rs.getInt("usuario_id"));
                doctor.setEspecialidadId(rs.getInt("especialidad_id"));
                doctor.setBiografia(rs.getString("biografia"));
            }
        }
        return doctor;
    }

    public List<Doctor> getAllDoctores() throws SQLException {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT m.*, u.nombre, u.apellido, u.foto_perfil, e.nombre as especialidad_nombre " +
                     "FROM medicos m " +
                     "JOIN usuarios u ON m.usuario_id = u.id " +
                     "JOIN especialidades e ON m.especialidad_id = e.id";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setUsuarioId(rs.getInt("usuario_id"));
                doctor.setEspecialidadId(rs.getInt("especialidad_id"));
                doctor.setBiografia(rs.getString("biografia"));
                
                // Crear y establecer el usuario
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFoto(rs.getString("foto_perfil"));
                doctor.setUsuario(usuario);
                
                // Crear y establecer la especialidad
                Especialidad especialidad = new Especialidad();
                especialidad.setId(rs.getInt("especialidad_id"));
                especialidad.setNombre(rs.getString("especialidad_nombre"));
                doctor.setEspecialidad(especialidad);
                
                doctores.add(doctor);
            }
        }
        return doctores;
    }

    public boolean insertDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO medicos (usuario_id, especialidad_id, biografia) VALUES (?, ?, ?)";
        System.out.println("DEBUG: Intentando insertar doctor para usuarioId: " + doctor.getUsuarioId());
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctor.getUsuarioId());
            stmt.setInt(2, doctor.getEspecialidadId());
            stmt.setString(3, doctor.getBiografia());
            
            int affectedRows = stmt.executeUpdate();
            System.out.println("DEBUG: insertDoctor executeUpdate affected rows: " + affectedRows);
            return affectedRows > 0;
        }
    }

    public boolean updateDoctor(Doctor doctor) throws SQLException {
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

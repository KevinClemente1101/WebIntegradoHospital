package com.mycompany.hospital_citas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    public Usuario getUsuarioByEmail(String email) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setDni(rs.getString("dni"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
            }
        }
        return usuario;
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setDni(rs.getString("dni"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
    
    // Insertar usuario (registro)
    public int insertUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, dni, fecha_nacimiento, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("DEBUG: Intentando insertar usuario con email: " + usuario.getEmail());
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getDni());
            stmt.setString(6, usuario.getFechaNacimiento());
            stmt.setString(7, usuario.getRol());
            
            int affectedRows = stmt.executeUpdate();
            System.out.println("DEBUG: executeUpdate affected rows: " + affectedRows);
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("DEBUG: Generated User ID: " + generatedId);
                        return generatedId;
                    }
                }
            }
            System.out.println("DEBUG: Failed to get generated ID or no rows affected.");
            return -1; // Retorna -1 si no se pudo obtener el ID
        }
    }

    // Actualizar usuario
    public boolean updateUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, dni = ?, fecha_nacimiento = ?, email = ?, password = ?, rol = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getDni());
            stmt.setString(4, usuario.getFechaNacimiento());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getPassword());
            stmt.setString(7, usuario.getRol());
            stmt.setInt(8, usuario.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Eliminar usuario
    public boolean deleteUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public void registrarUsuario(Usuario usuario) throws Exception {
        // Verificar si el email ya existe
        if (getUsuarioByEmail(usuario.getEmail()) != null) {
            throw new Exception("El email ya está registrado");
        }

        String sql = "INSERT INTO usuarios (nombre, apellido, dni, fecha_nacimiento, email, password, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getDni());
            stmt.setString(4, usuario.getFechaNacimiento());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getPassword());
            stmt.setString(7, usuario.getRol());
            stmt.executeUpdate();
        }
    }

    public Usuario getUsuarioByDni(String dni) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE dni = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setDni(rs.getString("dni"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
            }
        }
        return usuario;
    }

    // Obtener usuario por ID
    public Usuario getUsuarioById(int id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setDni(rs.getString("dni"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuario.setDescripcion_doctor(rs.getString("descripcion_doctor"));
                usuario.setFoto_doctor(rs.getString("foto_doctor"));
                 // Puedes añadir otros campos si los necesitas al obtener el usuario por ID
            }
        }
        return usuario;
    }

    // Obtener lista de usuarios por rol
    public List<Usuario> getUsuariosByRol(String rol) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rol);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setDni(rs.getString("dni"));
                usuario.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuario.setDescripcion_doctor(rs.getString("descripcion_doctor"));
                usuario.setFoto_doctor(rs.getString("foto_doctor"));
                // Puedes añadir otros campos si los necesitas al obtener usuarios por rol
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    // Actualizar información específica del doctor (descripción y foto)
    public boolean updateDoctorInfo(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET descripcion_doctor = ?, foto_doctor = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getDescripcion_doctor());
            stmt.setString(2, usuario.getFoto_doctor());
            stmt.setInt(3, usuario.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}

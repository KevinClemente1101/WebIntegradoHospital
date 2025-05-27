package com.mycompany.hospital_citas.dao;

import com.mycompany.hospital_citas.dto.UsuarioDTO;
import com.mycompany.hospital_citas.dto.UsuarioDTO;
import com.mycompany.hospital_citas.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    public UsuarioDTO getUsuarioByEmail(String email) throws SQLException {
        UsuarioDTO usuario = null;
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new UsuarioDTO();
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

    public List<UsuarioDTO> getAllUsuarios() throws SQLException {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO();
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
    public boolean insertUsuario(UsuarioDTO usuario) throws SQLException {
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
            return stmt.executeUpdate() > 0;
        }
    }

    // Actualizar usuario
    public boolean updateUsuario(UsuarioDTO usuario) throws SQLException {
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
    
    public void registrarUsuario(UsuarioDTO usuario) throws Exception {
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

    public UsuarioDTO getUsuarioByDni(String dni) throws SQLException {
        UsuarioDTO usuario = null;
        String sql = "SELECT * FROM usuarios WHERE dni = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new UsuarioDTO();
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
}

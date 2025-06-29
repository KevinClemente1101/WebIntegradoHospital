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
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setGenero(rs.getString("genero"));
                usuario.setEstado(rs.getBoolean("estado"));
                usuario.setFoto(rs.getString("foto_perfil"));
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
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setGenero(rs.getString("genero"));
                usuario.setEstado(rs.getBoolean("estado"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    // Insertar usuario (registro)
    public int insertUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, dni, telefono, direccion, fecha_nacimiento, genero, rol, estado, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("DEBUG: Intentando insertar usuario con email: " + usuario.getEmail());
        System.out.println("DEBUG: Género del usuario: " + usuario.getGenero());
        System.out.println("DEBUG: Teléfono del usuario: " + usuario.getTelefono());
        System.out.println("DEBUG: Dirección del usuario: " + usuario.getDireccion());

        // Validar que el género no sea null
        if (usuario.getGenero() == null || usuario.getGenero().trim().isEmpty()) {
            throw new SQLException("El campo género no puede estar vacío");
        }

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getDni());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getDireccion());
            stmt.setString(8, usuario.getFechaNacimiento());
            stmt.setString(9, usuario.getGenero());
            stmt.setString(10, usuario.getRol());
            stmt.setBoolean(11, usuario.isEstado());
            stmt.setString(12, usuario.getFoto());

            System.out.println("DEBUG: Parámetros preparados:");
            System.out.println("DEBUG: - Nombre: " + usuario.getNombre());
            System.out.println("DEBUG: - Apellido: " + usuario.getApellido());
            System.out.println("DEBUG: - Email: " + usuario.getEmail());
            System.out.println("DEBUG: - DNI: " + usuario.getDni());
            System.out.println("DEBUG: - Teléfono: " + usuario.getTelefono());
            System.out.println("DEBUG: - Dirección: " + usuario.getDireccion());
            System.out.println("DEBUG: - Fecha Nacimiento: " + usuario.getFechaNacimiento());
            System.out.println("DEBUG: - Género: " + usuario.getGenero());
            System.out.println("DEBUG: - Rol: " + usuario.getRol());
            System.out.println("DEBUG: - Estado: " + usuario.isEstado());
            System.out.println("DEBUG: - Foto: " + usuario.getFoto());

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
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, dni = ?, telefono = ?, direccion = ?, fecha_nacimiento = ?, email = ?, password = ?, genero = ?, rol = ?, estado = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getDni());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getDireccion());
            stmt.setString(6, usuario.getFechaNacimiento());
            stmt.setString(7, usuario.getEmail());
            stmt.setString(8, usuario.getPassword());
            stmt.setString(9, usuario.getGenero());
            stmt.setString(10, usuario.getRol());
            stmt.setBoolean(11, usuario.isEstado());
            stmt.setInt(12, usuario.getId());
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

        System.out.println("DEBUG: registrarUsuario - Género del usuario: " + usuario.getGenero());
        System.out.println("DEBUG: registrarUsuario - Teléfono del usuario: " + usuario.getTelefono());
        System.out.println("DEBUG: registrarUsuario - Dirección del usuario: " + usuario.getDireccion());

        // Validar que el género no sea null
        if (usuario.getGenero() == null || usuario.getGenero().trim().isEmpty()) {
            throw new Exception("El campo género no puede estar vacío");
        }

        String sql = "INSERT INTO usuarios (nombre, apellido, dni, telefono, direccion, fecha_nacimiento, email, password, genero, rol, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getDni());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getDireccion());
            stmt.setString(6, usuario.getFechaNacimiento());
            stmt.setString(7, usuario.getEmail());
            stmt.setString(8, usuario.getPassword());
            stmt.setString(9, usuario.getGenero());
            stmt.setString(10, usuario.getRol());
            stmt.setBoolean(11, usuario.isEstado());

            System.out.println("DEBUG: registrarUsuario - Parámetros preparados:");
            System.out.println("DEBUG: - Nombre: " + usuario.getNombre());
            System.out.println("DEBUG: - Apellido: " + usuario.getApellido());
            System.out.println("DEBUG: - DNI: " + usuario.getDni());
            System.out.println("DEBUG: - Teléfono: " + usuario.getTelefono());
            System.out.println("DEBUG: - Dirección: " + usuario.getDireccion());
            System.out.println("DEBUG: - Fecha Nacimiento: " + usuario.getFechaNacimiento());
            System.out.println("DEBUG: - Email: " + usuario.getEmail());
            System.out.println("DEBUG: - Género: " + usuario.getGenero());
            System.out.println("DEBUG: - Rol: " + usuario.getRol());
            System.out.println("DEBUG: - Estado: " + usuario.isEstado());

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
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setGenero(rs.getString("genero"));
                usuario.setEstado(rs.getBoolean("estado"));
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
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setGenero(rs.getString("genero"));
                usuario.setEstado(rs.getBoolean("estado"));
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
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setGenero(rs.getString("genero"));
                usuario.setEstado(rs.getBoolean("estado"));

                // No se cargan los campos de doctor, ya que no aplican a todos los roles
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

    public int countUsuarios() throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios";
        try (Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next())
                return rs.getInt(1);
        }
        return 0;
    }

    public int countUsuariosByRol(String rol) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE rol = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rol);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        }
        return 0;
    }

    // Actualizar solo foto_perfil
    public boolean actualizarFotoPerfil(int usuarioId, String fotoPerfil) throws SQLException {
        String sql = "UPDATE usuarios SET foto_perfil = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fotoPerfil);
            stmt.setInt(2, usuarioId);
            return stmt.executeUpdate() > 0;
        }
    }
}

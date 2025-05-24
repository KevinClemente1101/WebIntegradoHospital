package com.mycompany.hospital_citas;
import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDao {
    public List<Especialidad> getAllEspecialidades() throws SQLException {
        System.out.println("DEBUG: Iniciando getAllEspecialidades en EspecialidadDao");
        List<Especialidad> especialidades = new ArrayList<>();
        String sql = "SELECT * FROM especialidades";
        
        try (Connection conn = DBUtil.getConnection()) {
            System.out.println("DEBUG: Conexión a la base de datos establecida");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("DEBUG: Ejecutando consulta SQL: " + sql);
                while (rs.next()) {
                    Especialidad especialidad = new Especialidad();
                    especialidad.setId(rs.getInt("id"));
                    especialidad.setNombre(rs.getString("nombre"));
                    especialidad.setDescripcion(rs.getString("descripcion"));
                    especialidades.add(especialidad);
                    System.out.println("DEBUG: Especialidad cargada - ID: " + especialidad.getId() + ", Nombre: " + especialidad.getNombre());
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Error al obtener especialidades: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("ERROR: Error inesperado: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("Error al procesar las especialidades", e);
        }
        
        System.out.println("DEBUG: Total de especialidades cargadas: " + especialidades.size());
        return especialidades;
    }
    
    public Especialidad getEspecialidadById(int id) throws SQLException {
        String sql = "SELECT * FROM especialidades WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Especialidad especialidad = new Especialidad();
                especialidad.setId(rs.getInt("id"));
                especialidad.setNombre(rs.getString("nombre"));
                especialidad.setDescripcion(rs.getString("descripcion"));
                return especialidad;
            }
        }
        
        return null;
    }

    public boolean insertEspecialidad(Especialidad especialidad) throws SQLException {
        String sql = "INSERT INTO especialidades (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad.getNombre());
            stmt.setString(2, especialidad.getDescripcion());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateEspecialidad(Especialidad especialidad) throws SQLException {
        String sql = "UPDATE especialidades SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad.getNombre());
            stmt.setString(2, especialidad.getDescripcion());
            stmt.setInt(3, especialidad.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEspecialidad(int id) throws SQLException {
        String sql = "DELETE FROM especialidades WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public int countEspecialidades() throws SQLException {
        String sql = "SELECT COUNT(*) FROM especialidades WHERE estado=1";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}

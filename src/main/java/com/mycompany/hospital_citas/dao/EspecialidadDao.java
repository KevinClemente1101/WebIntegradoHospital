package com.mycompany.hospital_citas.dao;
import com.mycompany.hospital_citas.dto.EspecialidadDTO;
import com.mycompany.hospital_citas.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDao {
    public List<EspecialidadDTO> getAllEspecialidades() throws SQLException {
        List<EspecialidadDTO> especialidades = new ArrayList<>();
        String sql = "SELECT * FROM especialidades";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                EspecialidadDTO especialidad = new EspecialidadDTO();
                especialidad.setId(rs.getInt("id"));
                especialidad.setNombre(rs.getString("nombre"));
                especialidad.setDescripcion(rs.getString("descripcion"));
                especialidades.add(especialidad);
            }
        }
        
        return especialidades;
    }
    
    public EspecialidadDTO getEspecialidadById(int id) throws SQLException {
        String sql = "SELECT * FROM especialidades WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                EspecialidadDTO especialidad = new EspecialidadDTO();
                especialidad.setId(rs.getInt("id"));
                especialidad.setNombre(rs.getString("nombre"));
                especialidad.setDescripcion(rs.getString("descripcion"));
                return especialidad;
            }
        }
        
        return null;
    }

    public boolean insertEspecialidad(EspecialidadDTO especialidad) throws SQLException {
        String sql = "INSERT INTO especialidades (nombre) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad.getNombre());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateEspecialidad(EspecialidadDTO especialidad) throws SQLException {
        String sql = "UPDATE especialidades SET nombre = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad.getNombre());
            stmt.setInt(2, especialidad.getId());
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
}

package com.mycompany.hospital_citas.service;

import com.mycompany.hospital_citas.dao.CitasDao;
import com.mycompany.hospital_citas.dto.CitaDTO;
import java.sql.SQLException;
import java.util.List;

public class CitaService {
    private final CitasDao dao = new CitasDao();

    public List<CitaDTO> getUpcomingCitas(int usuarioId) throws SQLException {
        return dao.getUpcomingCitasByUsuario(usuarioId);
    }
    
    // Numero de citas pendientes
    public int getCountPendientes(int usuarioId) throws SQLException {
        return dao.countCitasByEstado(usuarioId, "pendiente");
    }

    // Numero de citas completadas
    public int getCountCompletadas(int usuarioId) throws SQLException {
        return dao.countCitasByEstado(usuarioId, "completada");
    }
    
    // Todas las citas de un usuario
    public List<CitaDTO> getHistorial(int usuarioId) throws SQLException {
        return dao.getHistorialCitasByUsuario(usuarioId);
    }

}

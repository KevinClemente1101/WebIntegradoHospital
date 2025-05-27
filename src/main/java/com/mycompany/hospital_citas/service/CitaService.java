package com.mycompany.hospital_citas.service;

import com.mycompany.hospital_citas.dao.CitasDao;
import com.mycompany.hospital_citas.dao.DoctorDao;
import com.mycompany.hospital_citas.dto.CitaDTO;
import java.sql.SQLException;
import java.util.List;

public class CitaService {
    private final CitasDao citasDao = new CitasDao();
    private final DoctorDao doctorDao = new DoctorDao();

    // Obtener las citas futuras de un paciente
    public List<CitaDTO> getUpcomingCitas(int usuarioId) throws SQLException {
        return citasDao.getUpcomingCitasByUsuario(usuarioId);
    }
    
    // Numero total de citas pendientes del sistema
    public int getTotalCitasPendientes() throws SQLException {
        return citasDao.countAllCitasByEstado("pendiente");
    }

    // Numero total de citas completadas del sistema
    public int getTotalCitasCompletadas() throws SQLException {
        return citasDao.countAllCitasByEstado("completada");
    }
    
    // Numero de citas pendientes de un paciente
    public int getCountPendientesByPatient(int usuarioId) throws SQLException {
        return citasDao.countCitasByPatientByEstado(usuarioId, "pendiente");
    }

    // Numero de citas completadas de un paciente
    public int getCountCompletadasByPatient(int usuarioId) throws SQLException {
        return citasDao.countCitasByPatientByEstado(usuarioId, "completada");
    }
    
    // Numero de citas pendientes de un doctor
    public int getCountPendientesByDoctor(int usuarioId) throws SQLException {
        int medicoId = getMedicoIdByUsuarioId(usuarioId);
        return citasDao.countCitasByDoctorByEstado(medicoId, "pendiente");
    }
    
    // Numero de citas completadas de un doctor
    public int getCountCompletadasByDoctor(int usuarioId) throws SQLException {
        int medicoId = getMedicoIdByUsuarioId(usuarioId);
        return citasDao.countCitasByDoctorByEstado(medicoId, "completada");
    }
    
    // Todas las citas de un usuario
    public List<CitaDTO> getHistorial(int usuarioId) throws SQLException {
        return citasDao.getHistorialCitasByUsuario(usuarioId);
    }
    
    public int getMedicoIdByUsuarioId(int usuarioId) throws SQLException {
        return doctorDao.findIdByUsuarioId(usuarioId);
    }


}

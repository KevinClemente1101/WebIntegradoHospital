package com.mycompany.hospital_citas.service;

import com.mycompany.hospital_citas.dao.CitasDao;
import com.mycompany.hospital_citas.dao.DoctorDao;
import com.mycompany.hospital_citas.dto.CitaDTO;
import java.sql.SQLException;
import java.util.List;

public class CitaService {
    private final CitasDao dao = new CitasDao();
    private final DoctorDao doctorDao = new DoctorDao();

    public List<CitaDTO> getUpcomingCitas(int usuarioId) throws SQLException {
        return dao.getUpcomingCitasByUsuario(usuarioId);
    }
    
    // Numero total de citas pendientes del sistema
    public int getTotalCitasPendientes() throws SQLException {
        return dao.countAllCitasByEstado("pendiente");
    }

    // Numero total de citas completadas del sistema
    public int getTotalCitasCompletadas() throws SQLException {
        return dao.countAllCitasByEstado("completada");
    }
    
    // Numero de citas pendientes de un paciente
    public int getCountPendientesByPatient(int usuarioId) throws SQLException {
        return dao.countCitasByPatientByEstado(usuarioId, "pendiente");
    }

    // Numero de citas completadas de un paciente
    public int getCountCompletadasByPatient(int usuarioId) throws SQLException {
        return dao.countCitasByPatientByEstado(usuarioId, "completada");
    }
    
    // Numero de citas pendientes de un doctor
    public int getCountPendientesByDoctor(int usuarioId) throws SQLException {
        int medicoId = getMedicoIdByUsuarioId(usuarioId);
        return dao.countCitasByDoctorByEstado(medicoId, "pendiente");
    }
    
    // Numero de citas completadas de un doctor
    public int getCountCompletadasByDoctor(int usuarioId) throws SQLException {
        int medicoId = getMedicoIdByUsuarioId(usuarioId);
        return dao.countCitasByDoctorByEstado(medicoId, "completada");
    }
    
    // Todas las citas de un usuario
    public List<CitaDTO> getHistorial(int usuarioId) throws SQLException {
        return dao.getHistorialCitasByUsuario(usuarioId);
    }
    
    public int getMedicoIdByUsuarioId(int usuarioId) throws SQLException {
        return doctorDao.findIdByUsuarioId(usuarioId);
    }


}

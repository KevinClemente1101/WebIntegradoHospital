package com.mycompany.hospital_citas;

import java.sql.Date;
import java.sql.Time;

public class Horario {
    private int id;
    private int doctor_id;
    private Date fecha_inicio;
    private Date fecha_fin;
    private Time hora_inicio;
    private Time hora_fin;
    private int intervalo_citas;
    private boolean estado;
    private String medicoNombre;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }
    
    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Time getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
    }

    public int getIntervalo_citas() {
        return intervalo_citas;
    }

    public void setIntervalo_citas(int intervalo_citas) {
        this.intervalo_citas = intervalo_citas;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }
    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }
}
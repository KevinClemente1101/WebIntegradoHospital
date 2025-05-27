package com.mycompany.hospital_citas.models;

import java.sql.Time;

public class Horarios {
    
    private int id;
    private int doctorId;
    private String diaSemana;
    private Time horaInicio;
    private Time horaFin;
    private int intervaloCitas;
    private int maxCitasDia;
    private int estado;

    public Horarios() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public int getIntervaloCitas() {
        return intervaloCitas;
    }

    public void setIntervaloCitas(int intervaloCitas) {
        this.intervaloCitas = intervaloCitas;
    }

    public int getMaxCitasDia() {
        return maxCitasDia;
    }

    public void setMaxCitasDia(int maxCitasDia) {
        this.maxCitasDia = maxCitasDia;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
}

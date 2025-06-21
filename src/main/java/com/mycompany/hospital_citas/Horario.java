package com.mycompany.hospital_citas;

import java.sql.Time;

public class Horario {
    private int id;
    private int doctor_id;
    private String dias_semana;
    private Time hora_inicio;
    private Time hora_fin;
    private int intervalo_citas;

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

    public String getDias_semana() {
        return dias_semana;
    }

    public void setDias_semana(String dias_semana) {
        this.dias_semana = dias_semana;
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
}
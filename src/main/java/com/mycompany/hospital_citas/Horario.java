package com.mycompany.hospital_citas;

import java.sql.Time;

public class Horario {
    private int id;
    private int doctor_id;
    private String diaSemana;
    private Time hora_inicio;
    private Time hora_fin;
    private boolean estado;

    public Horario(int id, int doctor_id, String diaSemana, Time hora_inicio, Time hora_fin, boolean estado) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.diaSemana = diaSemana;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.estado = estado;
    }

    public Horario() {
    }

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

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
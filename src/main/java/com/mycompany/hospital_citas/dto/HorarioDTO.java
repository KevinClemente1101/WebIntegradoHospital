package com.mycompany.hospital_citas.dto;

import java.sql.Time;

public class HorarioDTO {
    private int id;
    private String diaSemana;
    private Time horaInicio;

    public HorarioDTO(int id, String diaSemana, Time horaInicio) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }
}
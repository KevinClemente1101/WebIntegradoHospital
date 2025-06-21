package com.mycompany.hospital_citas;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HorarioAgrupado {
    private String diaInicio;
    private String diaFin;
    private Time horaInicio;
    private Time horaFin;
    private List<Integer> ids;

    public HorarioAgrupado() {
        this.ids = new ArrayList<>();
    }

    // Getters y Setters
    public String getDiaInicio() { return diaInicio; }
    public void setDiaInicio(String diaInicio) { this.diaInicio = diaInicio; }

    public String getDiaFin() { return diaFin; }
    public void setDiaFin(String diaFin) { this.diaFin = diaFin; }

    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }

    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }

    public List<Integer> getIds() { return ids; }
    public void setIds(List<Integer> ids) { this.ids = ids; }

    public void addId(int id) {
        this.ids.add(id);
    }
    
    // --- Métodos de ayuda para la vista ---

    /**
     * Devuelve los días formateados para mostrar en la tabla.
     * Ej: "Lunes", "Lunes a Viernes".
     */
    public String getDiasFormateados() {
        if (diaInicio.equals(diaFin)) {
            return diaInicio;
        } else {
            return diaInicio + " a " + diaFin;
        }
    }

    /**
     * Devuelve los IDs como una cadena separada por comas, para usar en el formulario.
     */
    public String getIdsParaBorrar() {
        return this.ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
} 
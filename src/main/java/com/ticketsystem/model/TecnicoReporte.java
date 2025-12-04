package com.ticketsystem.model;

public class TecnicoReporte {

    private String nombre;
    private int total;

    public TecnicoReporte() {
    }

    public TecnicoReporte(String nombre, int total) {
        this.nombre = nombre;
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

package com.ticketsystem.model;

public class InformeTecnico {

    private int idInforme;
    private String fechaCierre;
    private String observaciones;
    private int idIncidencia;
    private int idTecnico;

    // Datos extra (joins)
    private String descripcionIncidencia;
    private String nombreTecnico;

    public InformeTecnico() {
    }

    public int getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(int idInforme) {
        this.idInforme = idInforme;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getDescripcionIncidencia() {
        return descripcionIncidencia;
    }

    public void setDescripcionIncidencia(String descripcionIncidencia) {
        this.descripcionIncidencia = descripcionIncidencia;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }
}

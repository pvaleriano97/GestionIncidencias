
package com.ticketsystem.model;

import java.sql.Timestamp;
public class InformeTecnico {
private int idInforme;
    private Timestamp fechaCierre;
    private String observaciones;
    private int idIncidencia;
    // Campo para JOIN
    private String nombreTecnico;

    // Constructor, Getters y Setters
    public InformeTecnico() {}

    public int getIdInforme() { return idInforme; }
    public void setIdInforme(int idInforme) { this.idInforme = idInforme; }

    public Timestamp getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(Timestamp fechaCierre) { this.fechaCierre = fechaCierre; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }

    public String getNombreTecnico() { return nombreTecnico; }
    public void setNombreTecnico(String nombreTecnico) { this.nombreTecnico = nombreTecnico; }
}
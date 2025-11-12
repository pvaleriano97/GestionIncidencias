
package com.ticketsystem.model;

import java.sql.Timestamp;


public class HistorialEquipo {
    private int idHistorial;
    private int idEquipo;
    private String detalle;
    
    // Constructor, Getters y Setters
    public HistorialEquipo() {}

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    
}


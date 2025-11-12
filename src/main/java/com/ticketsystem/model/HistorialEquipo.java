/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketsystem.model;

import java.sql.Timestamp;

/**
 *
 * @author ROSA SANDOVAL
 */
public class HistorialEquipo {
    private int idHistorial;
    private int idEquipo;
    private String detalle;
    private Timestamp fechaRegistro; // Campo asumido para tracking
    
    // Constructor, Getters y Setters
    public HistorialEquipo() {}

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}


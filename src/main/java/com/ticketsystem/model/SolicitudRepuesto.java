
package com.ticketsystem.model;

import java.sql.Timestamp;
public class SolicitudRepuesto {
    private int idSolicitud;
    private int cantidad;
    private Timestamp fechaSolicitud;
    private int idIncidencia;
    private int idRepuesto;
    // Campos para JOIN
    private String nombreRepuesto; 

    // Constructor, Getters y Setters
    public SolicitudRepuesto() {}

    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Timestamp getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Timestamp fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }

    public int getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(int idRepuesto) { this.idRepuesto = idRepuesto; }
    
    public String getNombreRepuesto() { return nombreRepuesto; }
    public void setNombreRepuesto(String nombreRepuesto) { this.nombreRepuesto = nombreRepuesto; }
}

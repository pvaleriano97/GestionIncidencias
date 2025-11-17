package com.ticketsystem.model;

import java.util.Date;

public class SolicitudRepuesto {
private int idSolicitud;
private int idIncidencia;
private int idRepuesto;
private int cantidad;
private Date fechaSolicitud;
private String descripcion; // Incidencia
private String nombre;      // Repuesto
private String mensaje;     // Para mensajes de validación
private int stock; // agrega esto

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Getters y Setters
    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int id) { this.idSolicitud = id; }

    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }

    public int getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(int idRepuesto) { this.idRepuesto = idRepuesto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Date getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(Date fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}

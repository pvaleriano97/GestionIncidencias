package com.ticketsystem.model;

public class Tecnico {

    private int idTecnico;
    private String nombre;
    private String especialidad;
    private int idUsuario;
    private int disponibilidad;
    private int ticketsCerrados;
    private int totalResueltas;

    // Constructor vacío OBLIGATORIO
    public Tecnico() {
    }

    public Tecnico(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getTicketsCerrados() {
        return ticketsCerrados;
    }

    public void setTicketsCerrados(int ticketsCerrados) {
        this.ticketsCerrados = ticketsCerrados;
    }

    public int getTotalResueltas() {
        return totalResueltas;
    }

    public void setTotalResueltas(int totalResueltas) {
        this.totalResueltas = totalResueltas;
    }
}


package com.ticketsystem.model;

/**
 *
 * @author ROSA SANDOVAL
 */
public class Tecnico {
    private int idTecnico;
    private String nombre;
    private String especialidad;
  private int Disponibilidad;
    private int ticketsCerrados;
        private int totalResueltas;

    // Getters y Setters
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
    

    public int getTicketsCerrados() {
        return ticketsCerrados;
    }

    public void setTicketsCerrados(int ticketsCerrados) {
        this.ticketsCerrados = ticketsCerrados;
    }

    public int getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(int Disponibilidad) {
        this.Disponibilidad = Disponibilidad;
    }

 public int getTotalResueltas() {
        return totalResueltas;
    }

    public void setTotalResueltas(int totalResueltas) {
        this.totalResueltas = totalResueltas;
    }
}

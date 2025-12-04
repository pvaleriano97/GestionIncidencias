package com.ticketsystem.model;

public class Repuesto {
    private int idRepuesto;
    private String nombre;
    private int stock;
    private double costo;

    public Repuesto() {}

    public Repuesto(int idRepuesto, String nombre, int stock, double costo) {
        this.idRepuesto = idRepuesto;
        this.nombre = nombre;
        this.stock = stock;
        this.costo = costo;
    }

    // getters & setters
    public int getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(int idRepuesto) { this.idRepuesto = idRepuesto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
}

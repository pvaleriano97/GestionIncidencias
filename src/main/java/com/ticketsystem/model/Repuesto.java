
package com.ticketsystem.model;

import java.math.BigDecimal;
public class Repuesto {
    private int idRepuesto;
    private String nombre;
    private int stock;
    private BigDecimal costo;
    
    // Constructor, Getters y Setters
    public Repuesto() {}

    public int getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(int idRepuesto) { this.idRepuesto = idRepuesto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}

package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Farmacia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Direccion direccion;

    public Farmacia(){}

    public Farmacia(String nombre) {
        this.nombre = nombre;
    }
    public Farmacia(String nombreFarmacia, Direccion direccion) {
        this.nombre = nombreFarmacia;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.cglib.core.Local;
/*
 * No es necesario usar cascade aqui porque no esta estrictamente dependiente de una al otro
 * como onetoone
 * Localidad puede existir sin Direccion
 * 
 * Si borro farmacia, localidad no esta en la tabla farmacia
 * 
 * O sea hay una relacion entre Direccion y Localidad pero no son totalmente dependientes,
 * por lo tanto no hay efecto cascada cuando hago las cosas
 * Ahora tengo que pensar que en mis test tendre que guardar de forma separada Localidad
 * Porque si yo guardo una direccion con una localidad y no la guarde previamente se va romper
 */
@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private Integer numero;

    @ManyToOne
    private Localidad localidad;

    public Direccion(String calle, Integer numero, Localidad localidad) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;                  
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    

}

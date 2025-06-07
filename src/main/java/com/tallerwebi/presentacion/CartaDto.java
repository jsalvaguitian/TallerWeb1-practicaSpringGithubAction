package com.tallerwebi.presentacion;

import java.util.Map;
import com.tallerwebi.dominio.Carta;
public class CartaDto {
    private Long id;
    private  String nombre;

    //Hay clases que te niegan la construccion vacia como la clase estatica
    //private CartaDto(){}


    //PASAR UNA ENTIDAD A DTO
    public CartaDto(Carta cartaEntidad){
        this.id = cartaEntidad.getId();
        this.nombre = cartaEntidad.getNombre();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }


    public Carta obtenerEntidad(){
        Carta carta = new Carta();
        return this.obtenerEntidad(carta);
    }

     public Carta obtenerEntidad(Carta cartaEntidad){
        cartaEntidad.setId(this.id);
        cartaEntidad.setNombre(this.nombre);
        return cartaEntidad;
    }
}

package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.presentacion.CartaDto;

public interface RepositorioCarta {

    Boolean crear(Carta obtenerEntidad);
    Carta obtenerPorId(Long id);
    List<Carta>obtener();
    void actualizar(Carta carta);

}

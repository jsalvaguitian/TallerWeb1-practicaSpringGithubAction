package com.tallerwebi.dominio;

import java.util.List;


public interface RepositorioCarta {

    Boolean crear(Carta obtenerEntidad);
    Carta obtenerPorId(Long id);
    List<Carta>obtener();
    void actualizar(Carta carta);

}

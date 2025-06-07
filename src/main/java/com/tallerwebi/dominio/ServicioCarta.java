package com.tallerwebi.dominio;

import org.springframework.transaction.annotation.Transactional;

import com.tallerwebi.presentacion.CartaDto;

@Transactional
public interface ServicioCarta {
    Boolean crear(CartaDto carta);

}

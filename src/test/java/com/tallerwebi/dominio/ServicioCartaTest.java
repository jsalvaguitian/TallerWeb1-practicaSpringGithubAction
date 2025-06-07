package com.tallerwebi.dominio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tallerwebi.presentacion.CartaDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ServicioCartaTest {

    private RepositorioCarta repositorioCarta;
    private ServicioCarta servicioCarta;

    @BeforeEach
    public void init(){
        this.repositorioCarta = mock(RepositorioCarta.class);
         this.servicioCarta = new ServicioCartaImpl(this.repositorioCarta);
    }

    @Test
    public void cuandoCreoUnaCartaEntoncesObtengoUnResultadoPositivo(){

        when(repositorioCarta.crear(any())).thenReturn(true);
        Boolean creado = servicioCarta.crear(mock(CartaDto.class));
        assertThat(creado, is(true));
    }

   

}

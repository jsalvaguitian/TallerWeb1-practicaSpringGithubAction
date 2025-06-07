package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.ServicioCarta;
import com.tallerwebi.dominio.ServicioCartaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorCartaTest {

    private ServicioCarta servicioCarta;
    private ControladorCarta controladorCarta;

    @BeforeEach
    public void init(){
        servicioCarta = mock(ServicioCarta.class);
        controladorCarta = new ControladorCarta(servicioCarta);
    }
    @Test
    public void dadoQueSePuedaCrearCartasCuandoCreoUnaObtengoUnMensajeDeExito(){
        //preparacion
        Carta cartaMock = mock(Carta.class);
        CartaDto cartaDto = new CartaDto(cartaMock);
        //carta.setNombre("Carta 1");

        when(servicioCarta.crear(cartaDto)).thenReturn(true);//nos desprendemos de la implementacion del servicio
        //ejecucion
        ModelAndView modelAndView = controladorCarta.crearCarta(cartaDto);

        //verificacion
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Carta creada correctamente";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));

    }

    @Test
    public void dadoQueSePuedaCrearCartasCuandoIntenteCrearUnaCartaDeUnMensajeDeError(){
        //preparacion
        Carta cartaMock = mock(Carta.class);
        CartaDto carta = new CartaDto(cartaMock);
        carta.setNombre("");

        when(servicioCarta.crear(carta)).thenReturn(false);

        //ejecucion
        ModelAndView modelAndView = controladorCarta.crearCarta(carta);

        //verificacion
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Error al crear la carta";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));

    }
}

package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCartaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ControladorCartaTest {

    private ServicioCartaImpl servicioCarta;
    private ControladorCarta controladorCarta;

    @BeforeEach
    public void init(){
        servicioCarta = new ServicioCartaImpl();
        controladorCarta = new ControladorCarta(servicioCarta);
    }
    @Test
    public void dadoQueSePuedaCrearCartasCuandoCreoUnaObtengoUnMensajeDeExito(){
        //preparacion
        CartaDto carta = new CartaDto();
        carta.setNombre("Carta 1");

        /*this.servicioCarta = new ServicioCartaImpl();
        this.controladorCarta = new  ControladorCarta(servicioCarta);*/

        //ejecucion
        ModelAndView modelAndView = controladorCarta.crearCarta(carta);

        //verificacion
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Carta creada correctamente";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));

    }

    @Test
    public void dadoQueSePuedaCrearCartasCuandoIntenteCrearUnaCartaDeUnMensajeDeError(){
        //preparacion
        CartaDto carta = new CartaDto();
        carta.setNombre("");

        //ejecucion
        ModelAndView modelAndView = controladorCarta.crearCarta(carta);

        //verificacion
        String vistaEsperada = "crear-carta";
        String mensajeEsperado = "Error al crear la carta";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("mensaje")));

    }
}

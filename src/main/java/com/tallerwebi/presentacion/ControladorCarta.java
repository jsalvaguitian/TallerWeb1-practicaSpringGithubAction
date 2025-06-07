package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarta;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class ControladorCarta {

    private  ServicioCarta servicioCarta;

    public ControladorCarta(ServicioCarta servicioCarta2) {
        this.servicioCarta = servicioCarta2;
    }

    public ModelAndView crearCarta(CartaDto carta) {
        ModelMap modelMap = new ModelMap();

        Boolean creada = this.servicioCarta.crear(carta);
        String mensaje = "Error al crear la carta";

        if (creada) {
             mensaje = "Carta creada correctamente";
        }
        modelMap.put("mensaje", mensaje);
        modelMap.put("carta", carta);//en vez de darle new, le doy 
        return  new ModelAndView("crear-carta", modelMap);
    }
}

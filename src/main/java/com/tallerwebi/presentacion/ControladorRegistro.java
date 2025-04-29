package com.tallerwebi.presentacion;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class ControladorRegistro {

    public ModelAndView registrar(String email, String password) {

        if(email.isEmpty()){
            return crearModelView("El mail es obligatorio");
        }
        if(password.isEmpty()){
            return crearModelView("El password es obligatorio");
        }

        return new ModelAndView("login");
    }

    private ModelAndView crearModelView(String mensajeError) {
        ModelMap map = new ModelMap();
        map.put("mensajeError", mensajeError);
        return new ModelAndView("registro", map);
    }



}

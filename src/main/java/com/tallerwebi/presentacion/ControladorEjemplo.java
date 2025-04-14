package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEjemplo {

    @RequestMapping("/ejemplo")
    public ModelAndView ejemplo() {
        ModelMap modelo = new ModelMap();
        modelo.put("nombrePersona", "Jesica");
        return new ModelAndView("ejemplo", modelo);
    }

}

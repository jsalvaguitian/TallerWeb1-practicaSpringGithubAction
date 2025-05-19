package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PasswordLongitudIncorrectaException;
import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioExistenteExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    private ServicioRegistro servicioRegistro;

    @Autowired
    public ControladorRegistro(ServicioRegistro servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }
    public ModelAndView registrar(String email, String password) {

        if(email.isEmpty()){
            return crearModelView("El mail es obligatorio");
        }
        if(password.isEmpty()){
            return crearModelView("El password es obligatorio");
        }
        try{
           servicioRegistro.registrar(email, password);
        }catch (PasswordLongitudIncorrectaException exception){
            return crearModelView("El password debe tener al menos 5 caracteres");
        }catch (UsuarioExistenteExcepcion excepcion){
            return crearModelView("El usuario ya existe");
        }catch (Exception exception){
            return crearModelView("ocurrio un error en el registro");
        }
        /* Esto ya no xq usaremos excepciones
        Usuario usuarioCreado =servicioRegistro.registrar(email, password);
        if(usuarioCreado == null){
            return crearModelView("El password debe tener al menos 5 caracteres");
        }

         */
        return new ModelAndView("login");
    }

    private ModelAndView crearModelView(String mensajeError) {
        ModelMap map = new ModelMap();
        map.put("mensajeError", mensajeError);
        return new ModelAndView("registro", map);
    }



}

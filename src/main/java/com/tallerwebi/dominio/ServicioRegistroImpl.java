package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {
    @Override
    public Usuario registrar(String email, String password) {
        if(password.length()<5){
            //return null;
            throw new PasswordLongitudIncorrectaException();
        }
        return new Usuario();
    }
}
/*
* uso de excepciones es necesario en SERVICIOS
 * en caso si tengo varias validaciones como no registrar un usuario si ya existe el mail
 * o contrasenia invalida. Si devuelvo true o false / objeto o null no me brinda informacion que es lo
 * que fallo en cambio excepciones si.
 *
 * El controlador va detectar la respuesta del servicios si esta fallando por un email
 * o por un usuario que existe
* */

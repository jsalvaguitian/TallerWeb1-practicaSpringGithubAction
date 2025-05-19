package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioRegistroTest {

    /*
    Si hay usuario y contrasenia el registro es exitoso
    si la contrasenia tiene menos de 5 caracteres el registro falla
    */

    //podemos usar constantes para mail y password y asi reusarlo

    /* IMPORTANTE: En servicio registro de implementacion tiene la validacion del largo del usuario.
    Tanto el controlador como el servicio tiene logica de validacion pero la logica de validacion de cada uno
    se diferencia en que.
    EL SERVICIO tiene que ver a la LOGICA DE NEGOCIO
    En cambio en el CONTROLADOR tiene que ver con algo mal que este ingresando el usuario.
    En el controlador no puedo poner validaciones de si la contrasenia tiene una longitud mas de 5.
    Porque si yo tengo otro cliente que me dice yo me conformo con que la contrasenia sea 5 long y otro cliente
    me pide 20 de long.

    Que el email tiene que estar en el formulario eso ya no tiene que ver con el negocio sino del INPUT del usuario
    Si hay una dependencia entre ellos, se debera esforzar de hacer test unitario independientemente si estan conectado o no
    Aqui entra en juego Mockito

    &&&
    todo A AVERIGUAR: no entendi que el mock fuese el motivo del porque no usar en el constructor del ControladorRegistro  el ServicioRegImplementacion. El mock crea objetos apartir de solamente interfaces que yo le envie? y no con clases?
    &&&

    Y al hacer siempre un MOCK necesito decirle como se tiene que comportar practicamente programarlo desde cero
    * */
    ServicioRegistro servicioRegistro = new ServicioRegistroImpl();//obligatorio usar la implementacion para testear servicios
    //porque aqui queremos probar nuestro Servicio y por eso es obligatorio instancioarlo en cambio en el test de controlador
    //usaremos mockito

    @Test
    public void siHayEmailYPasswordElRegistroEsExitoso(){
        givenUsuarioNoExiste();
        Usuario usuarioCreado= whenRegistroUsuario("jesi@mail.com", "123456");
        thenElRegistroEsExitoso(usuarioCreado);
    }



    private void givenUsuarioNoExiste() {}

    private Usuario whenRegistroUsuario(String email, String password) {
        Usuario usuarioCreado = servicioRegistro.registrar(email,password);
        return usuarioCreado;
    }
    /*
    En el controlador era para validar si era exitoso me envia al login, si el controlador me
    llevaba a la vista de registro + msj de error no exitoso
    *
    En el servicio no validamos como los controladores */
    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado, is(notNullValue()));
    }

    @Test
    public void siLaPasswordTieneMenosDeCincoCacracteresElRegistroFalla(){
        givenUsuarioNoExiste();
        assertThrows(PasswordLongitudIncorrectaException.class, ()-> whenRegistroUsuario("jesi@mail.com", "123"));
        /*
        assertThrows
        Le enviamos como parametros QUE EXCEPCION SE VA LANZAR?
        Y CUANDO SE VA A VALIDAR?
        cuando se ejecute servicioRegistro.registrar()
        me lanzas la exepcion y ese va ser mi caso de fallo

        funcion anonima ()->
         */
        /* AL USAR EXCEPCIONES  YA NO USAMOS ESTO
        Usuario usuarioCreado = whenRegistroUsuario("jesi@mail.com", "123");
        thenElRegistroFalla(usuarioCreado);
        */
    }

    private void thenElRegistroFalla(Usuario usuarioCreado) {
        assertThat(usuarioCreado, is(nullValue()));
    }
}

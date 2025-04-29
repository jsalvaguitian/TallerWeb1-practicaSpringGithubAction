package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;


public class ControladorRegistroTest {
    /*
    * Para registrar a un usuario necesito mail y contrasenia.
    * Si no existe email el registro deberia fallar.
    * Si no existe contrasenia el registro deberia fallar.
    *
    * NOTA: En la parte de preparacion habra que encapsular los datos u objetos
    * a usar en metodos como
    *
    * givenNoExisteUsuario()
    * givenExistenDirectores();
    * givenExistenCategorias();
    *
    * */

    private final String email = "jesi@gmail.com";
    private final String password = "1234";

     @Test
    public void conEmailYPasswordElRegistroEsExitoso(){
         //preparacion --> given()
         givenNoExisteElUsuario();

         //ejecucion --> when()
         ModelAndView vistaModelada = whenRegistroUsuario(email, password);

         //comprobacion o validacion --> then()
         thenElRegistroEsExitoso(vistaModelada);
     }

     @Test void siNoHayPaswordElRegistroFalla(){
         givenNoExisteElUsuario();
         //ejecucion
         ModelAndView vistaModelada = whenRegistroUsuario(email, "");

         //validacion
         thenElRegistroFalla(vistaModelada, "El password es obligatorio");
     }



    @Test
     public void siNoHayEmailElRegistroFalla(){
         //preparacion --> given()
         givenNoExisteElUsuario();

         //ejecucion
         ModelAndView vistaModelada = whenRegistroUsuario("", password);

         //validacion
         thenElRegistroFalla(vistaModelada, "El mail es obligatorio");
     }

    private void thenElRegistroFalla(ModelAndView vistaModelada, String mensaje) {
         assertThat(vistaModelada.getViewName(),equalToIgnoringCase("registro"));
         assertThat(vistaModelada.getModel().get("mensajeError").toString(),equalToIgnoringCase(mensaje));
    }

    private void thenElRegistroFallaPorPassword(ModelAndView vistaModelada) {
        assertThat(vistaModelada.getViewName(),equalToIgnoringCase("registro"));
        assertThat(vistaModelada.getModel().get("mensajeError").toString(),equalToIgnoringCase("El password es obligatorio"));
    }

    private void thenElRegistroFallaPorNombre(ModelAndView vistaModelada) {
        assertThat(vistaModelada.getViewName(),equalToIgnoringCase("registro"));
        assertThat(vistaModelada.getModel().get("mensajeError").toString(),equalToIgnoringCase("El nombre es obligatorio"));
    }
    private void givenNoExisteElUsuario() {

    }
    private ModelAndView whenRegistroUsuario(String email, String password) {
         ControladorRegistro controladorRegistro = new ControladorRegistro();
         ModelAndView vistaModelada = controladorRegistro.registrar(email,password);
         return vistaModelada;
    }

    private void thenElRegistroEsExitoso(ModelAndView vistaModelada) {
         //validaQue("nombre de la vista que tengo", "nombre de la vista que espero")
        assertThat(vistaModelada.getViewName(), equalToIgnoringCase("Login"));
    }


}

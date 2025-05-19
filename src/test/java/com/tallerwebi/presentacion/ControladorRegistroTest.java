package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PasswordLongitudIncorrectaException;
import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
//IMPORTANTE PUEDO DEBUGGEAR LOS TEST haciendo clic derecho de mi test y selecciona debug test

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
    /*
    INTRODUCCION TEST UNITARIO CON MOCK
    Para darnos cuenta si existe una dependecia con un ServicioRegistro, veremos que
    esta inyectado en el constructor del controladorRegistro.
    Si existe esa dependencia la rompemos en el test unitario porque solo se probara
    la logica del controlador y lo hacemos sin usar la verdadera implementacion de servicio
    sino que usaremos MOCK.

    QUE ES UN MOCK?
    Un mock es una clase que imita el comportamiento del ServicioRegistro pero no es mi verdadero
    ServicioRegistro

    mock(ServiocioRegisto) --> objeto que implementa esa interfaz

    Usaremos mock cualquier cosa q el controlador dependa
    */
    private ServicioRegistro servicioRegistro = mock(ServicioRegistro.class);
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
         ControladorRegistro controladorRegistro = new ControladorRegistro(servicioRegistro);
         /* ANTES
         ControladorRegistro controladorRegistro = new ControladorRegistro();

         Habiamos usado un controlador vacio por defecto ya que no estabamos usando servicios
         pero ahora lo requerimos
         */
         ModelAndView vistaModelada = controladorRegistro.registrar(email,password);
         return vistaModelada;
    }

    private void thenElRegistroEsExitoso(ModelAndView vistaModelada) {
         //validaQue("nombre de la vista que tengo", "nombre de la vista que espero")
        assertThat(vistaModelada.getViewName(), equalToIgnoringCase("Login"));
    }

    /****************************************************************/
    /* Luego de hacer tdd de servicioRegistro sobre la validacion de longitud de contrasenia
    * Tenemos que definir en el controladorRegistro que pasa cuando el usuario va registrarse
    * con una pws de 5 caracteres a que vista le mando? Que msj le doy si no cumple con la regla de
    * negocio?
    * */

    @Test//caso que esta relacionado con el SERVICIO REGISTRO
    public void siLaPasswordTieneMenosDe5CaracteresElRegistroFalla(){
        givenNoExisteElUsuario();
        //seteo el comportamiemto del mock servicioRegistro 1era OPCION
        doThrow(PasswordLongitudIncorrectaException.class)
                .when(servicioRegistro).registrar(email,password);
        //2DA OPCION
        when(servicioRegistro.registrar(email, password)).thenThrow(PasswordLongitudIncorrectaException.class);
        //otro tipo de seteo de mock segun el CASO DE PRUEBA
        when(servicioRegistro.registrar(email, password)).thenReturn(new Usuario());

        //por defecto el mock devuelve null pero si lo queremos explicito podemos hacer esto
        when(servicioRegistro.registrar(email, password)).thenReturn(null);





        //antes de ejecutar estas lineas del test tengo que setear el comportamiento del mock
        ModelAndView mav= whenRegistroUsuario(email, password);
        thenElRegistroFalla(mav, "El password debe tener al menos 5 caracteres");
    }


}
/* dia 15-05
* Se hizo el test del controlador en donde que pasa con las vista si
* tengo al fallo con una pswd menos de 5 caracteres
*
* Dio rojo porque no estaba inyectando el  servicioRegistro en el controladorRegistro
* y nos llevo a aplicarlo en el controlador
* y luego en el ControladorRegistroTest usar una imitacion de ServicioRegistro
*
*
* */

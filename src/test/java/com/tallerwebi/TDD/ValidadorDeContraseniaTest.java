package com.tallerwebi.TDD;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ValidadorDeContraseniaTest {
    /*
    * Nombre de los metodos TEST
    * DeberiaDevolver............cuando.....
    * DeberiaRetornar............cuando......
    * */

    @Test
    public void queDevuelvaINVALIDOCuandoLaContraseniaTieneUnSoloCaracter() {
        ValidadorDeContrasenia validador = new ValidadorDeContrasenia("1");
        String fortaleza = validador.evaluarFortaleza();
        assertThat(fortaleza, equalToIgnoringCase("INVALIDO"));
    }

    @Test
    public void queDevuelvaDEBILCuandoLaContraseniaTiene8Caracteres() {
        ValidadorDeContrasenia validador = new ValidadorDeContrasenia("belenn12");
        String fortaleza = validador.evaluarFortaleza();

        assertThat(fortaleza, equalToIgnoringCase("DEBIL"));
    }

    @Test
    public void queRetorneMEDIANACuandoLaContraseniaEsBel33n12() {
        ValidadorDeContrasenia validador = new ValidadorDeContrasenia("bel33n12");
        String fortaleza = validador.evaluarFortaleza();

        assertThat(fortaleza, equalToIgnoringCase("MEDIANA"));
    }

    

}

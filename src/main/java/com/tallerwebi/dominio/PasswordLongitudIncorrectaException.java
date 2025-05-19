package com.tallerwebi.dominio;
/*
Lo hacemos asi para que no sea una excepcion ya checkeada y no tengamos q
hacer throws en todos los metodos como al hacer extend Exception
* */
public class PasswordLongitudIncorrectaException extends RuntimeException {
}

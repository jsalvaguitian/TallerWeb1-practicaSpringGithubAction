package com.tallerwebi.TDD;

public class ValidadorDeContrasenia {
    private String contrasenia;

    ValidadorDeContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String evaluarFortaleza() {
        if(contrasenia.length() >= 8){
            int cantidadDeNumeros = 0;
            for(int i = 0; i < contrasenia.length(); i++){
                if(Character.isDigit(contrasenia.charAt(i))){
                    cantidadDeNumeros++;
                }
            }
            if(cantidadDeNumeros >= 4)
                return "MEDIANA";

            return "DEBIL";
        }
        return "INVALIDO";

    }
}

package com.example.teddyv2.utils;

import android.util.Patterns;

/**
 * Esta clase proporciona un punto en comun para las validaciones de la aplicacion para reducir la
 * repeticion de codigo a la hora de validar campos de entrada. Todas las validaciones deberian
 * estar en esta clase para su facil uso y modificacion.
 * <br>
 * Los servicios ofrecidos por esta clase son:
 * <ul>
 *     <li>Comprobar que un texto no este vacio</li>
 *     <li>Comprobar que un texto sea una contrasenna valida</li>
 *     <li>Comprobar que un texto sea una direccion de correo valida</li>
 *     <li>Comprobar que un texto sea un telefono valido</li>
 * </ul>
 */
public class ValidationUtils {

    /**
     * Determina si un texto esta vacio.
     *
     * @param text texto que se desea validar
     * @return {@code true} si el texto no esta vacio o {@code false} en caso contrario
     */
    public static boolean isNotNull(String text){
        return (text != null && !text.equals(""));
    }

    /**
     * Determina si un texto constituye una contrasenna valida.
     *
     * Una Contrasenna es considerada valida si tiene una longitud superior a 5.
     *
     * @param text texto que se desea validar
     * @return {@code true} si el texto es contrasenna valida {@code false} en caso contrario
     */
    public static boolean isValidPassword(String text){
        return (isNotNull(text) && text.length() > 5);
    }

    /**
     * Determina si un texto constituye una direccion de correo valida.
     *
     * @param text texto que se desea validar
     * @return {@code true} si el texto es una direccion de correo valida o {@code false} en
     *          caso contrario
     */
    public static boolean isValidEmail(String text){
        return (isNotNull(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches());
    }

    /**
     * Determina si un texto constituye un numero de telefono valido.
     *
     * @param text texto que se desea validar
     * @return {@code true} si el texto es un numero de telefono valido o {@code false} en caso
     *          contrario
     */
    public static boolean isValidPhone(String text){
        return (isNotNull(text) && Patterns.PHONE.matcher(text).matches());
    }

}

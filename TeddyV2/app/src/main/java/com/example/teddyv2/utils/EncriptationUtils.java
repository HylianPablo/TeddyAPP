package com.example.teddyv2.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase que permite encriptar y desencriptar los datos personales del usuario. Aunque en la vida
 * real no seria un sistema viable de encriptacion, podemos usarlo para simular uno en la practica.
 * <br>
 * Los servicios ofrecidos por esta clase son:
 * <ul>
 *     <li>Encriptar una cadena de caracteres</li>
 *     <li>Desencriptar una cadena de caracteres</li>
 * </ul>
 */
public class EncriptationUtils {

    private static final char[] HEXACHARS =
            {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    private static final byte ASCII_ZERO = 48;
    private static final byte ASCII_A_MINUS_TEN = 55;

    /**
     * Encripta una cadena de caracteres, convirtiendo cada caracter a su valor ascii
     * correspondiente en hexadecimal.
     *
     * @param data datos que queremos encriptar
     * @return datos encriptados
     */
    public static String encrypt(String data){
        StringBuilder sb = new StringBuilder();
        int b;
        for(int i=0; i < data.length(); i++){
            b = data.charAt(i);
            sb.append(HEXACHARS[b/16]);
            sb.append(HEXACHARS[b%16]);
        }

        return sb.toString();
    }

    /**
     * Desencripta una cadena de caracteres codificado en hexadecimal segun su valor ascii.
     *
     * @param data datos que queremos desencriptar
     * @return datos desencriptados
     */
    public static String decrypt(String data){
        StringBuilder sb = new StringBuilder();
        int b;
        for(int i=0; i < data.length(); i+=2){
            b = getNumericValue(data.charAt(i)) * 16;
            b += getNumericValue(data.charAt(i+1));
            sb.append((char) b);
        }
        return null;
    }

    /**
     * Obtenemos el valor en base 10 de los numeros hexadecimales.
     * @param c caracter hexadecimal
     * @return valor en base 10 del caracter hexadecimal
     */
    private static int getNumericValue(char c){
        if(c < 'A'){
            return (byte) (c - ASCII_ZERO);
        }else{
            return (byte) (c - ASCII_A_MINUS_TEN);
        }
    }

    /**
     * Encripta una cadena en SHA1.
     * @param input cadena a encriptar
     * @return cadena encriptada en SHA1
     */
    public static String sha1(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }catch (Exception e){
            return  null;
        }

    }
}

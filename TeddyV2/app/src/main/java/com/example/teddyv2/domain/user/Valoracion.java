package com.example.teddyv2.domain.user;

import com.example.teddyv2.data.model.LoggedInUser;

import java.util.HashMap;


public class Valoracion {

    private LoggedInUser usuarioValorador;
    private User usuarioValorado;
    private int puntuacion;
    private String comentario;

    public Valoracion(User usuarioValorado, LoggedInUser usuarioValorador){
        this.usuarioValorado = usuarioValorado;
        this.usuarioValorador = usuarioValorador;
        puntuacion = 0;
        comentario = null;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }

    public String getNameOfUsuarioValorado(){
        return usuarioValorado.getName();
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("idUsuarioValora", usuarioValorador.getUserId());
        mapa.put("idUsuarioValorado", usuarioValorado.getUsername());
        mapa.put("puntuacion", puntuacion);
        mapa.put("comentario", comentario);
        return mapa;
    }
}

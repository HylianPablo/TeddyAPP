package com.example.teddyv2.domain.matches;


import android.util.Log;

import com.example.teddyv2.domain.user.UserLevel;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Match {

    private Date fecha;
    private String idOrganizador;
    private String idPista;
    private MatchType modalidad;
    private UserLevel nivel;
    private List<String> participantes;
    private String id;


    public Match() {
        fecha =null;
        idOrganizador = "";
        idPista = "";
        modalidad = null;
        nivel = null;
        participantes = null;
    }

    public Match(Timestamp fecha, String idOrganizador, String idPista, int modalidad, int nivel) {
        this.fecha = fecha.toDate();
        this.idOrganizador = idOrganizador;
        this.idPista = idPista;
        this.modalidad = MatchType.getTypeByInt(modalidad);
        this.nivel = UserLevel.getUserLevelByNumber(nivel);
        participantes = new ArrayList<String>();
        participantes.add(idOrganizador);
    }



    public Match(Map<String, Object> mapa, String id) {
        fecha =((Timestamp) mapa.get("fecha")).toDate();
        idOrganizador = (String) mapa.get("idOrganizador");
        idPista = (String) mapa.get("idPista");
        modalidad = MatchType.getTypeByInt(Long.valueOf((long) mapa.get("modalidad")).intValue());
        nivel = UserLevel.getUserLevelByNumber(Long.valueOf((long) mapa.get("nivel")).intValue());
        participantes = (List<String>) mapa.get("participantes");
        this.id = id;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("fecha", new Timestamp(fecha));
        mapa.put("idOrganizador", idOrganizador);
        mapa.put("idPista", idPista);
        mapa.put("modalidad", MatchType.getIntByType(this.modalidad));
        mapa.put("nivel", UserLevel.getNumberByLevel(this.nivel));
        mapa.put("participantes", participantes);
        return mapa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdOrganizador() {
        return idOrganizador;
    }

    public void setIdOrganizador(String idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public MatchType getModalidad() {
        return modalidad;
    }

    public void setModalidad(MatchType modalidad) {
        this.modalidad = modalidad;
    }

    public UserLevel getNivel() {
        return nivel;
    }

    public void setNivel(UserLevel nivel) {
        this.nivel = nivel;
    }

    public boolean addParticipante(String nuevoParticipante){
        if (participantes.contains(nuevoParticipante)) return  false;
        int size=2;
        if(modalidad == MatchType.SINGLES) size = 2;
        if(modalidad == MatchType.DOUBLES) size = 4;
        if (participantes.size()+1>size) return false;
        participantes.add(nuevoParticipante);
        return true;
    }


    public String getDay(){
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return format.format(fecha);
    }

    public String getHour(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return format.format(fecha);
    }

    public int getSize(){
        if(modalidad == MatchType.SINGLES) return 2;
        return 4;
    }

    public ArrayList<String> getRestoParticipantes(String p){
        ArrayList<String> resultado = new ArrayList<>(participantes.size()-1);
        for(String s : participantes){
            if(!s.equals(p)) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public String getId() {
        return id;
    }
}

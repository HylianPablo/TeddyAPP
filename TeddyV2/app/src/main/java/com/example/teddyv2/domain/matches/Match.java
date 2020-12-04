package com.example.teddyv2.domain.matches;


import com.example.teddyv2.domain.user.UserLevel;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Match {

    private Date fecha;
    private String idOrganizador;
    private String idPista;
    private MatchType modalidad;
    private UserLevel nivel;


    public Match() {
        fecha =null;
        idOrganizador = "";
        idPista = "";
        modalidad = null;
        nivel = null;
    }

    public Match(Map<String, Object> mapa) {
        fecha =((Timestamp) mapa.get("fecha")).toDate();
        idOrganizador = (String) mapa.get("idOrganizador");
        idPista = (String) mapa.get("idPista");
        modalidad = MatchType.getTypeByInt(Long.valueOf((long) mapa.get("modalidad")).intValue());
        nivel = UserLevel.getUserLevelByNumber(Long.valueOf((long) mapa.get("nivel")).intValue());
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> mapa = new HashMap<String, Object>();
        mapa.put("fecha", new Timestamp(fecha));
        mapa.put("idOrganizador", idOrganizador);
        mapa.put("idPista", idPista);
        mapa.put("modalidad", MatchType.getIntByType(this.modalidad));
        mapa.put("nivel", UserLevel.getNumberByLevel(this.nivel));
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



}

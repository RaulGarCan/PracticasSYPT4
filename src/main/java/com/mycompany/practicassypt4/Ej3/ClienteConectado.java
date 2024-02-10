package com.mycompany.practicassypt4.Ej3;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

public class ClienteConectado implements Serializable {
    private int idCliente;
    private LocalDateTime fechaHoraConexion;
    private ArrayList<Integer> idsPeticionados;
    private ArrayList<Profesor> profesoresEnviados;
    private LocalDateTime fechaHoraDesconexion;
    private long duracionConexion;

    public ClienteConectado(int idCliente, LocalDateTime fechaHoraConexion, LocalDateTime fechaHoraDesconexion) {
        this.idCliente = idCliente;
        this.fechaHoraConexion = fechaHoraConexion;
        this.idsPeticionados = new ArrayList<>();
        this.profesoresEnviados = new ArrayList<>();
        this.fechaHoraDesconexion = fechaHoraDesconexion;
        this.duracionConexion = calcularTiempoTotalConexion();
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaHoraConexion() {
        return fechaHoraConexion;
    }

    public void setFechaHoraConexion(LocalDateTime fechaHoraConexion) {
        this.fechaHoraConexion = fechaHoraConexion;
    }

    public LocalDateTime getFechaHoraDesconexion() {
        return fechaHoraDesconexion;
    }

    public void setFechaHoraDesconexion(LocalDateTime fechaHoraDesconexion) {
        this.fechaHoraDesconexion = fechaHoraDesconexion;
    }

    public long getDuracionConexion() {
        return duracionConexion;
    }

    public void setDuracionConexion(long duracionConexion) {
        this.duracionConexion = duracionConexion;
    }

    public long calcularTiempoTotalConexion(){
        return fechaHoraConexion.until(fechaHoraDesconexion, ChronoUnit.SECONDS);
    }
    public ArrayList<Integer> getIdsPeticionados() {
        return idsPeticionados;
    }

    public void setIdsPeticionados(ArrayList<Integer> idsPeticionados) {
        this.idsPeticionados = idsPeticionados;
    }

    public ArrayList<Profesor> getProfesoresEnviados() {
        return profesoresEnviados;
    }

    public void setProfesoresEnviados(ArrayList<Profesor> profesoresEnviados) {
        this.profesoresEnviados = profesoresEnviados;
    }
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> mapa = new HashMap<>();
        mapa.put("idCliente",idCliente);
        mapa.put("fechaHoraConexion",fechaHoraConexion);
        mapa.put("fechaHoraDesconexion",fechaHoraDesconexion);
        mapa.put("duracionConexion",duracionConexion);
        mapa.put("idsPeticionados",idsPeticionados);
        mapa.put("profesoresEnviados",profesoresEnviados);
        return mapa;
    }
}

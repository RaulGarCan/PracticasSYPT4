package com.mycompany.practicassypt4.Ej1;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private String emisor;
    private String receptor;
    private String contenido;

    public Mensaje(String emisor, String receptor, String contenido) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "\nEmisor: "+emisor+"\nContenido: "+contenido;
    }
}

package Ej2;

import java.io.Serializable;

public class Correo implements Serializable {
    private String origen;
    private String destinatario;
    private String asunto;
    private String mensaje;

    public Correo(String origen, String destinatario, String asunto, String mensaje) {
        this.origen = origen;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Correo{" +
                "origen='" + origen + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", asunto='" + asunto + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}

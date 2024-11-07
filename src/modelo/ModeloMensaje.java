package modelo;

import java.io.Serializable;
import java.util.Date;

public class ModeloMensaje implements Serializable {
    private String remitente;
    private String destinatario;
    private String contenido;
    private String tipo; // "texto", "imagen", "video"
    private Date fecha;

    public ModeloMensaje(String remitente, String destinatario, String contenido, String tipo) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.tipo = tipo;
        this.fecha = new Date(); // Asignar la fecha actual
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "remitente='" + remitente + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", contenido='" + contenido + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
package controller;

import modelo.ModeloMensaje;
import java.util.ArrayList;
import java.util.List;

public class ControladorMensaje {
    private List<ModeloMensaje> mensajes;

    public ControladorMensaje() {
        this.mensajes = new ArrayList<>();
    }

    public void enviarMensaje(ModeloMensaje mensaje) {
        mensajes.add(mensaje);
        // Aquí podrías agregar lógica para enviar el mensaje a otros clientes
    }

    public List<ModeloMensaje> obtenerMensajes() {
        return mensajes;
    }

    public List<ModeloMensaje> obtenerMensajesPorUsuario(String idUsuario) {
        List<ModeloMensaje> mensajesUsuario = new ArrayList<>();
        for (ModeloMensaje mensaje : mensajes) {
            if (mensaje.getRemitente().equals(idUsuario) || mensaje.getDestinatario().equals(idUsuario)) {
                mensajesUsuario.add(mensaje);
            }
        }
        return mensajesUsuario;
    }
}
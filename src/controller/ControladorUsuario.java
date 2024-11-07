package controller;

import modelo.ModeloUsuario;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario {
    private List<ModeloUsuario> usuarios;

    public ControladorUsuario() {
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(ModeloUsuario usuario) {
        usuarios.add(usuario);
    }

    public ModeloUsuario buscarUsuario(String id) {
        for (ModeloUsuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null; // o lanzar una excepción
    }

    public List<ModeloUsuario> obtenerUsuarios() {
        return usuarios;
    }

    public void eliminarUsuario(String id) {
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }
}
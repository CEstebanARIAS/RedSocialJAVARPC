package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {
    private JTextField campoNombre;
    private JButton botonIniciar;

    public VentanaLogin() {
        setTitle("Inicio de Sesión");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel etiquetaNombre = new JLabel("Nombre:");
        campoNombre = new JTextField();
        botonIniciar = new JButton("Iniciar Sesión");

        panel.add(etiquetaNombre);
        panel.add(campoNombre);
        panel.add(new JLabel()); // Espacio vacío
        panel.add(botonIniciar);

        add(panel);
        
        // Acción del botón
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                // Lógica para iniciar sesión (enviar al controlador, etc.)
                System.out.println("Iniciando sesión como: " + nombre);
            }
        });
    }

    public String getNombreUsuario() {
        return campoNombre.getText();
    }
}
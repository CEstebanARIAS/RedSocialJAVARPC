 package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaChat extends JFrame {
    private JTextArea areaTextoMensajes;
    private JTextField campoMensaje;
    private JButton botonEnviar;

    public VentanaChat() {
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        areaTextoMensajes = new JTextArea();
        areaTextoMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTextoMensajes);
        
        campoMensaje = new JTextField();
        botonEnviar = new JButton("Enviar");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(botonEnviar, BorderLayout.EAST);

        panel.add(panelInferior, BorderLayout.SOUTH);
        
        add(panel);
        
        // Acción del botón
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = campoMensaje.getText();
                // Lógica para enviar el mensaje (enviar al controlador, etc.)
                System.out.println("Enviando mensaje: " + mensaje);
                campoMensaje.setText(""); // Limpiar el campo de entrada
            }
        });
    }

    public void agregarMensaje(String mensaje) {
        areaTextoMensajes.append(mensaje + "\n");
    }
}
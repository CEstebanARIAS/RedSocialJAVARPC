package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class clienteView {
    private JFrame frame;
    private JTextArea areaTexto;
    private JTextField campoMensaje;
    private JButton botonEnviarMensaje;
    private JButton botonEnviarArchivo;
    private JButton botonSolicitarArchivo;

    public clienteView(String nombreUsuario) {
        frame = new JFrame("CHAT DE " + nombreUsuario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        campoMensaje = new JTextField("Escriba aquí su mensaje");
        campoMensaje.setForeground(Color.GRAY);

        // Agregar espacio en el campo de mensaje
        campoMensaje.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Márgenes internos

        // Agregar FocusListener para el marcador de posición
        campoMensaje.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoMensaje.getText().equals("Escriba aquí su mensaje")) {
                    campoMensaje.setText("");
                    campoMensaje.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campoMensaje.getText().isEmpty()) {
                    campoMensaje.setText("Escriba aquí su mensaje");
                    campoMensaje.setForeground(Color.GRAY);
                }
            }
        });

        // Configuración de los botones y sus colores
        botonEnviarMensaje = new JButton("Enviar Mensaje");
        botonEnviarArchivo = new JButton("Enviar Archivo");
        botonSolicitarArchivo = new JButton("Solicitar Archivo");

        botonEnviarMensaje.setBackground(Color.GREEN);
        botonEnviarArchivo.setBackground(Color.ORANGE);
        botonSolicitarArchivo.setBackground(Color.BLUE);
        botonEnviarMensaje.setForeground(Color.WHITE);
        botonEnviarArchivo.setForeground(Color.WHITE);
        botonSolicitarArchivo.setForeground(Color.WHITE);

        // Bordes redondeados para los botones y márgenes para espaciar
        botonEnviarMensaje.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        botonEnviarArchivo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        botonSolicitarArchivo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Panel inferior para el campo de mensaje y botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Espacio alrededor del panel inferior
        panelInferior.add(campoMensaje, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(botonEnviarArchivo);
        panelBotones.add(botonSolicitarArchivo);
        panelBotones.add(botonEnviarMensaje);

        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panelInferior, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void agregarListenerBotonEnviarMensaje(ActionListener listener) {
        botonEnviarMensaje.addActionListener(listener);
    }

    public void agregarListenerBotonEnviarArchivo(ActionListener listener) {
        botonEnviarArchivo.addActionListener(listener);
    }

    public void agregarListenerBotonSolicitarArchivo(ActionListener listener) {
        botonSolicitarArchivo.addActionListener(listener);
    }

    public String getCampoMensaje() {
        return campoMensaje.getText();
    }

    public void limpiarCampoMensaje() {
        campoMensaje.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        areaTexto.append(mensaje + "\n");
    }

    public String solicitarNombreArchivo() {
        return JOptionPane.showInputDialog("Ingresa el nombre del archivo a solicitar:");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

        campoMensaje = new JTextField();

        botonEnviarMensaje = new JButton("Enviar Mensaje");
        botonEnviarArchivo = new JButton("Enviar Archivo");
        botonSolicitarArchivo = new JButton("Solicitar Archivo");

        JPanel panelInferior = new JPanel(new BorderLayout());
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

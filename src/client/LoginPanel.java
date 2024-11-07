package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel {
    private JFrame loginFrame;
    private JTextField campoUsuario;

    public LoginPanel() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150);
        loginFrame.setLocationRelativeTo(null); // Centrar la ventana

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Nombre de usuario:");
        campoUsuario = new JTextField();

        JButton botonLogin = new JButton("Iniciar Sesión");
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = campoUsuario.getText().trim();
                if (!nombreUsuario.isEmpty()) {
                    abrirClienteChat(nombreUsuario);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Por favor, ingrese un nombre de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(campoUsuario, BorderLayout.CENTER);
        panel.add(botonLogin, BorderLayout.SOUTH);

        loginFrame.getContentPane().add(panel);
        loginFrame.setVisible(true);
    }

    private void abrirClienteChat(String nombreUsuario) {
        // Cerrar el panel de login
        loginFrame.dispose();
        // Abrir el cliente de chat con el nombre de usuario proporcionado
        new Cliente(nombreUsuario);
    }
}

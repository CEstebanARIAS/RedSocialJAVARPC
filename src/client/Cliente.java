package client;

import utils.FileUtils;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class Cliente {
    private static final String DIRECCION_SERVIDOR = "192.168.0.13"; // Cambia esto si es necesario
    private static final int PUERTO = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea areaTexto;
    private String nombreUsuario;

    public Cliente(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario; // Guardar el nombre de usuario

        JFrame frame = new JFrame("CHAT DE " + nombreUsuario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Centrar la ventana

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        
        //Crear Campo de texto
        JTextField campoMensaje = new JTextField();
        
        // Crear botones
        JButton botonEnviarMensaje = new JButton("Enviar Mensaje");
        JButton botonEnviarArchivo = new JButton("Enviar Archivo");
        JButton botonSolicitarArchivo = new JButton("Solicitar Archivo");

        //Listener para los botones
        botonEnviarMensaje.addActionListener(e -> {
            enviarMensaje(campoMensaje.getText());
            campoMensaje.setText("");
        });
        botonEnviarArchivo.addActionListener(e -> enviarArchivo());
        botonSolicitarArchivo.addActionListener(e -> solicitarArchivo());
        
        // Configuración del panel inferior con el campo de mensaje 
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        
        //Configurar panel de botones para mostrarlos debajo de caja de mensaje
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(botonEnviarArchivo);
        panelBotones.add(botonSolicitarArchivo);
        panelBotones.add(botonEnviarMensaje);
        
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panelInferior, BorderLayout.SOUTH);
        frame.setVisible(true);

        try {
            socket = new Socket(DIRECCION_SERVIDOR, PUERTO);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Enviar el nombre de usuario al servidor al conectarse
            out.println("Usuario " + nombreUsuario + " Esta conectado");

            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = in.readLine()) != null) {
                        areaTexto.append(mensaje + "\n");
                        if (mensaje.startsWith("FILE:")) {
                            manejarArchivoRecibido(mensaje);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    
                }
            }).start();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    private void enviarMensaje(String mensaje) {
        if (mensaje != null && !mensaje.trim().isEmpty()) {
            out.println(nombreUsuario + ": " + mensaje); // Incluir el nombre de usuario en el mensaje
        }
    }

    private void enviarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String base64File = FileUtils.encodeFileToBase64(selectedFile.getAbsolutePath());
                out.println("FILE:" + selectedFile.getName() + ":" + base64File);
                areaTexto.append("Enviando archivo: " + selectedFile.getName() + "\n");
            } catch (IOException e) {
                areaTexto.append("No se pudo enviar el archivo desde cliente "+e.getMessage()+"\n");
            }
        }
    }
   
    private void solicitarArchivo() {
    // Mostrar un cuadro de diálogo para que el usuario ingrese el nombre del archivo
    String nombreArchivo = JOptionPane.showInputDialog("Ingresa el nombre del archivo a solicitar:");

    // Verificar que el usuario haya ingresado un nombre válido
    if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
        // Enviar solicitud de archivo al servidor
        out.println("SOLICITAR_ARCHIVO:" + nombreArchivo);
        
        // Mostrar mensaje en el área de texto para informar al usuario
        areaTexto.append("Solicitud de archivo enviada: " + nombreArchivo + "\n");
    } else {
        // Informar al usuario que el nombre del archivo es inválido
        JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de archivo válido.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void manejarArchivoRecibido(String mensaje) {
        try {
            // Dividir el mensaje para obtener el nombre del archivo y el contenido en Base64
            String[] partes = mensaje.split(":", 3);
            String nombreArchivo = partes[1];
            String contenidoBase64 = partes[2];

            // Definir la ruta donde se guardará el archivo en el cliente
            String rutaCliente = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "archivos_recibidos";
            Path directorio = Paths.get(rutaCliente);

            // Crear el directorio si no existe
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }

            // Ruta completa del archivo en el cliente
            String rutaArchivo = rutaCliente + File.separator + nombreArchivo;

            // Decodificar el archivo y guardarlo en la ruta especificada
            FileUtils.decodeBase64ToFile(contenidoBase64, rutaArchivo);
        
            // Notificar al usuario que el archivo ha sido recibido y guardado
            areaTexto.append("Archivo recibido y guardado en: " + rutaArchivo + "\n");
        } catch (IOException e) {
            areaTexto.append("Error al guardar el archivo recibido.\n");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Mostrar primero el panel de login
        new LoginPanel();
    }
}

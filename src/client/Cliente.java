package client;

import utils.FileUtils;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import view.clienteView;

public class Cliente {
    private static final String DIRECCION_SERVIDOR = "192.168.138.19";
    private static final int PUERTO = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nombreUsuario;
    private clienteView vista;

    public Cliente(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.vista = new clienteView(nombreUsuario);

        iniciarConexion();
        configurarListeners();
    }

    private void iniciarConexion() {
        try {
            socket = new Socket(DIRECCION_SERVIDOR, PUERTO);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(nombreUsuario);

            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = in.readLine()) != null) {
                        vista.mostrarMensaje(mensaje);
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

    private void configurarListeners() {
        vista.agregarListenerBotonEnviarMensaje(e -> {
            enviarMensaje(vista.getCampoMensaje());
            vista.limpiarCampoMensaje();
        });
        vista.agregarListenerBotonEnviarArchivo(e -> enviarArchivo());
        vista.agregarListenerBotonSolicitarArchivo(e -> solicitarArchivo());
    }

    private void enviarMensaje(String mensaje) {
        if (mensaje != null && !mensaje.trim().isEmpty()) {
            out.println(nombreUsuario + ": " + mensaje);
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
                vista.mostrarMensaje("Enviando archivo: " + selectedFile.getName());
            } catch (IOException e) {
                vista.mostrarMensaje("No se pudo enviar el archivo desde cliente " + e.getMessage());
            }
        }
    }

    private void solicitarArchivo() {
        String nombreArchivo = vista.solicitarNombreArchivo();
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            out.println("SOLICITAR_ARCHIVO:" + nombreArchivo);
            vista.mostrarMensaje("Solicitud de archivo enviada: " + nombreArchivo);
        } else {
            vista.mostrarError("Debe ingresar un nombre de archivo válido.");
        }
    }

    private void manejarArchivoRecibido(String mensaje) {
        String rutaCliente = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "archivos_recibidos";
        Path path = Paths.get(rutaCliente);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Directorio 'recibidos' creado en: " + path.toString());
            } catch (IOException e) {
                System.err.println("Error al crear el directorio 'recibidos': " + e.getMessage());
            }
        }
        try {
            String[] partes = mensaje.split(":", 3);
            String nombreArchivo = partes[1];
            String contenidoBase64 = partes[2];
            

            FileUtils.decodeBase64ToFile(contenidoBase64, rutaCliente + File.separator + nombreArchivo);
            vista.mostrarMensaje("Archivo recibido y guardado en: " + rutaCliente + File.separator + nombreArchivo);
        } catch (IOException e) {
            vista.mostrarMensaje("Error al guardar el archivo recibido.");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginPanel(); // Este panel debe llamar a `new Cliente(nombreUsuario);` tras el login exitoso
    }
}

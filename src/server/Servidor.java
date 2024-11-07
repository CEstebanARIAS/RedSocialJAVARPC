package server;

import utils.FileUtils;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Servidor {
    private static final int PUERTO = 12345;
    private static Set<PrintWriter> escritores = new HashSet<>();

    public static void main(String[] args) {
        // Especificar la ruta completa para el directorio "recibidos"
        Path path = Paths.get("C:\\Users\\asusl\\OneDrive\\Desktop\\recibidos");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Directorio 'recibidos' creado en: " + path.toString());
            } catch (IOException e) {
                System.err.println("Error al crear el directorio 'recibidos': " + e.getMessage());
            }
        }

        System.out.println("Servidor escuchando en el puerto " + PUERTO);
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                new ClienteHandler(serverSocket.accept()).start();
                System.out.println("cliente nuevo conectado");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClienteHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (escritores) {
                    escritores.add(out);
                }

                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    System.out.println("Mensaje de " + mensaje);
                    if (mensaje.startsWith("FILE:")) {
                        procesarArchivo(mensaje);
                    } else if (mensaje.startsWith("SOLICITAR_ARCHIVO:")) {
                        String nombreArchivo = mensaje.split(":")[1];
                        manejarSolicitudArchivo(nombreArchivo);
                    } else {
                        enviarATodos(mensaje); // Mensaje de chat
                    }
                }
            } catch (IOException e) {
                System.out.println("No se pudo procesar la solicitud del cliente:" + e.getMessage());
            } finally {
                try {
                    socket.close();
                    System.out.println("Socket Cerrado");
                } catch (IOException e) {

                    System.out.println("No se pudo cerrar el socket" + e.getMessage());
                }
                synchronized (escritores) {
                    escritores.remove(out);
                    System.out.println("Cliente desconectao");
                }
            }
        }

        private void procesarArchivo(String mensaje) {
            String[] partes = mensaje.split(":");
            String nombreArchivo = partes[1];
            String contenidoBase64 = partes[2];

            // Decodificar el archivo y guardarlo
            try {
                FileUtils.decodeBase64ToFile(contenidoBase64, "C:\\Users\\asusl\\OneDrive\\Desktop\\recibidos\\" + nombreArchivo);
                System.out.println("Archivo recibido: " + nombreArchivo);
                // Notificar a todos los clientes sobre el archivo recibido
                enviarATodos("Archivo en servidor: " + nombreArchivo); // Solo enviar el nombre del archivo
            } catch (IOException e) {
                
                System.out.println("No se pudo recibir el archivo"+ e.getMessage());
            }
        }

        private void manejarSolicitudArchivo(String nombreArchivo) {
            System.out.println("SOLICITUD DE ARCHIVO RECIBIDA");
            // Ruta del archivo en el servidor
            
            Path rutaArchivo = Paths.get("C:\\Users\\asusl\\OneDrive\\Desktop\\recibidos", nombreArchivo);
            
            // Verificar si el archivo existe
            if (Files.exists(rutaArchivo)) {
                try {
                    // Leer el archivo y codificarlo en Base64
                    
                    String contenidoBase64 = FileUtils.encodeFileToBase64(rutaArchivo.toString());

                    // Enviar el archivo codificado al cliente
                    out.println("FILE:" + nombreArchivo + ":" + contenidoBase64);
                    System.out.println("Archivo " + nombreArchivo + " enviado al cliente.");
                } catch (IOException e) {
                    System.err.println("Error al leer o codificar el archivo: " + e.getMessage());
                }
            } else {
                // Enviar mensaje de error si el archivo no se encuentra
                out.println("ERROR: El archivo solicitado no existe en el servidor.");
                System.out.println("El archivo " + nombreArchivo + " no se encontró en el servidor.");
            }
        }   
        
        
        private void enviarATodos(String mensaje) {
            synchronized (escritores) {
                for (PrintWriter escritor : escritores) {
                    escritor.println(mensaje);
                }
            }
        }
    }
}
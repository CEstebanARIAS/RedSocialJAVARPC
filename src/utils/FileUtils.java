package utils;

import java.io.*;
import java.nio.file.*;
import java.util.Base64;

public class FileUtils {
    // Convierte un archivo en una cadena Base64
    public static String encodeFileToBase64(String filePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    // Convierte una cadena Base64 en un archivo
    public static void decodeBase64ToFile(String base64String, String outputPath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        Files.write(Paths.get(outputPath), decodedBytes);
    }
}
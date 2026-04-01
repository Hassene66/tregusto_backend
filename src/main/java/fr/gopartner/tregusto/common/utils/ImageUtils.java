package fr.gopartner.tregusto.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtils {

    private ImageUtils() {
    }

    public static String fileToBase64(String baseDir, String relativePath, long maxFileSizeBytes) {
        try {
            Path filePath = Paths.get(baseDir, relativePath);

            if (!Files.exists(filePath)) {
                return null;
            }

            long fileSize = Files.size(filePath);
            if (fileSize > maxFileSizeBytes) {
                return null;
            }

            byte[] fileBytes = Files.readAllBytes(filePath);
            String base64 = Base64.getEncoder().encodeToString(fileBytes);

            String extension = getFileExtension(relativePath);
            String mimeType = getMimeType(extension);

            return "data:" + mimeType + ";base64," + base64;

        } catch (IOException e) {
            return null;
        }
    }

    private static String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot + 1) : "";
    }

    private static String getMimeType(String extension) {
        return switch (extension.toLowerCase()) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }
}

package fr.gopartner.tregusto.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileUtils {

    private FileUtils() {
    }

    public static String saveFile(String baseDir, String fileName, MultipartFile file) throws IOException {

        // Check if the file's name contains invalid characters'
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        // Fallback to default directory if baseDir is null or empty
        baseDir = Objects.requireNonNullElse(baseDir, "uploads/default").trim();
        if (baseDir.isEmpty()) {
            baseDir = "uploads/default";
        }

        // Ensure the upload directory exists
        var uploadDir = Path.of(baseDir).toAbsolutePath();
        Files.createDirectories(uploadDir);

        // Sanitize filename
        var cleanedFileName = sanitizeFileName(fileName);

        // Add file extension if necessary
        if (cleanedFileName.isEmpty() || !cleanedFileName.contains(".")) {
            cleanedFileName += "." + getExtension(file);
        }

        // Build final path
        var filePath = uploadDir.resolve(cleanedFileName);

        // Create parent dir if necessary (defensive)
        Files.createDirectories(filePath.getParent());

        // Transfer file
        file.transferTo(filePath.toFile());

        return filePath.toString();

    }

    /**
     * Extract file extension safely
     */
    private static String getExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null || !name.contains(".")) {
            return "";
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private static String sanitizeFileName(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

}

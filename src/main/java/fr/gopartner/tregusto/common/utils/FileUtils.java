package fr.gopartner.tregusto.common.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    private FileUtils() {
    }

    public static String saveFile(String baseDir, String category, String entityId, MultipartFile file, long maxFileSize, List<String> allowedExtensions) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (extension == null || !allowedExtensions.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("File type not allowed: " + extension);
        }

        Path categoryPath = Paths.get(baseDir, category);
        if (!Files.exists(categoryPath)) {
            Files.createDirectories(categoryPath);
        }

        String filename = entityId + "-" + UUID.randomUUID().toString() + "." + extension;
        Path filePath = categoryPath.resolve(filename);

        Files.write(filePath, file.getBytes());

        return category + "/" + filename;
    }

    public static boolean deleteFile(String baseDir, String relativePath) {
        try {
            Path filePath = Paths.get(baseDir, relativePath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean deleteDirectory(String baseDir, String category) {
        try {
            Path dirPath = Paths.get(baseDir, category);
            if (!Files.exists(dirPath)) {
                return true;
            }
            Files.walk(dirPath)
                .sorted((a, b) -> -a.compareTo(b))
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        // ignore
                    }
                });
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean fileExists(String baseDir, String relativePath) {
        return Files.exists(Paths.get(baseDir, relativePath));
    }
}

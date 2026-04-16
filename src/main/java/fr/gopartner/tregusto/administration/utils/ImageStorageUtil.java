package fr.gopartner.tregusto.administration.utils;

import fr.gopartner.tregusto.common.utils.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ImageStorageUtil {

    private ImageStorageUtil() {
    }

    public static String saveCategoryImage(String baseDir, Integer categoryId, MultipartFile file, long maxFileSize, List<String> allowedExtensions) throws IOException {
        return FileUtils.saveFile(baseDir, "categories", categoryId.toString(), file, maxFileSize, allowedExtensions);
    }

    public static void deleteCategoryImages(String baseDir, Integer categoryId) {
        FileUtils.deleteFile(baseDir, "categories/" + categoryId + "-" + categoryId + ".");
    }

    public static String saveProductImage(String baseDir, Integer productId, MultipartFile file, long maxFileSize, List<String> allowedExtensions) throws IOException {
        return FileUtils.saveFile(baseDir, "products", productId.toString(), file, maxFileSize, allowedExtensions);
    }

    public static void deleteProductMainImage(String baseDir, Integer productId) {
        FileUtils.deleteDirectory(baseDir, "products");
    }

    public static void deleteProductGalleryImage(String baseDir, Integer productId, Integer imageId) {
        FileUtils.deleteDirectory(baseDir, "products");
    }

    public static void deleteAllProductGalleryImages(String baseDir, Integer productId) {
        FileUtils.deleteDirectory(baseDir, "products");
    }

    public static void deleteAllProductImages(String baseDir, Integer productId) {
        FileUtils.deleteDirectory(baseDir, "products");
    }
}

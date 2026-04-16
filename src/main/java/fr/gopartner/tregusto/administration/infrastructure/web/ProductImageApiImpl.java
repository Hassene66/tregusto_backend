package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ProductImageDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductImagesApi;
import fr.gopartner.tregusto.administration.domain.menu.ProductImage;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ProductImageMapper;
import fr.gopartner.tregusto.administration.internal.menu.ProductImageService;
import fr.gopartner.tregusto.administration.internal.menu.ProductService;
import fr.gopartner.tregusto.common.config.ImageUploadConfig;
import fr.gopartner.tregusto.common.exception.shared.FileSystemException;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import fr.gopartner.tregusto.common.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProductImageApiImpl implements ProductImagesApi, ApiV1Administration {

    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;
    private final ImageUploadConfig imageUploadConfig;
    private final ProductService productService;

    @Override
    public ResponseEntity<ProductImageDTO> addProductImage(Integer productId, MultipartFile image, String altText, Integer displayOrder) {
        var product = productService.getById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Integer maxOrder = productImageService.getMaxDisplayOrder(productId);
        Integer newDisplayOrder = displayOrder != null ? displayOrder : maxOrder + 1;

        try {
            String imageUrl = FileUtils.saveFile(
                    imageUploadConfig.getBaseDir(),
                    product.getCategory().getSlug() + "/" + product.getId(),
                    productId.toString(),
                    image,
                    imageUploadConfig.getMaxFileSize(),
                    imageUploadConfig.getAllowedExtensions()
            );

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(imageUrl);
            productImage.setAltText(altText);
            productImage.setDisplayOrder(newDisplayOrder);
            productImage.setProduct(product);

            var created = productImageService.add(productId, productImage);
            return ResponseEntity.status(201).body(productImageMapper.toDto(created));
        } catch (IOException e) {
            throw new FileSystemException("Failed to save product image");
        }
    }

    @Override
    public ResponseEntity<ProductImageDTO> updateProductImage(Integer productId, Integer imageId, MultipartFile image, String altText, Integer displayOrder) {
        try {
            var updated = productImageService.updateWithImage(productId, imageId, image, altText, displayOrder);
            return ResponseEntity.ok(productImageMapper.toDto(updated));
        } catch (IOException e) {
            throw new FileSystemException("Failed to update product image");
        }
    }

    @Override
    public ResponseEntity<Void> deleteProductImage(Integer productId, Integer imageId) {
        productImageService.deleteWithFile(productId, imageId);
        return ResponseEntity.noContent().build();
    }
}

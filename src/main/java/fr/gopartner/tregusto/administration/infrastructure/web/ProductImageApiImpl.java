package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ProductImagesApi;
import fr.gopartner.tregusto.administration.api.generated.ProductImageDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductImageRequestDTO;
import fr.gopartner.tregusto.administration.domain.Product;
import fr.gopartner.tregusto.administration.domain.ProductImage;
import fr.gopartner.tregusto.administration.infrastructure.config.UploadProperties;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ProductImageMapper;
import fr.gopartner.tregusto.administration.internal.ProductImageService;
import fr.gopartner.tregusto.administration.internal.ProductService;
import fr.gopartner.tregusto.administration.utils.ImageStorageUtil;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import fr.gopartner.tregusto.common.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductImageApiImpl implements ProductImagesApi, ApiV1Administration {

    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;
    private final UploadProperties uploadProperties;
    private final ProductService productService;

    @Override
    public ResponseEntity<ProductImageDTO> addProductImage(Integer productId, MultipartFile image, String altText, Integer displayOrder) {
        var product = productService.getById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Integer maxOrder = productImageService.getMaxDisplayOrder(productId);
        Integer newDisplayOrder = displayOrder != null ? displayOrder : maxOrder + 1;

        try {
            String imageUrl = FileUtils.saveFile(
                uploadProperties.getBaseDir(),
                "products",
                productId.toString(),
                image,
                uploadProperties.getMaxFileSize(),
                uploadProperties.getAllowedExtensions()
            );

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(imageUrl);
            productImage.setAltText(altText);
            productImage.setDisplayOrder(newDisplayOrder);
            productImage.setProduct(product);

            var created = productImageService.add(productId, productImage);
            return ResponseEntity.status(201).body(productImageMapper.toDto(created));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save product image", e);
        }
    }

    @Override
    public ResponseEntity<ProductImageDTO> updateProductImage(Integer productId, Integer imageId, ProductImageRequestDTO request) {
        var entity = productImageMapper.toEntity(request);
        var updated = productImageService.update(productId, imageId, entity);
        return ResponseEntity.ok(productImageMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> deleteProductImage(Integer productId, Integer imageId) {
        productImageService.delete(productId, imageId);
        return ResponseEntity.noContent().build();
    }
}

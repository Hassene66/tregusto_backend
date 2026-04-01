package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ProductDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductRequestDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductsApi;
import fr.gopartner.tregusto.administration.domain.ProductImage;
import fr.gopartner.tregusto.administration.domain.ProductStatus;
import fr.gopartner.tregusto.administration.infrastructure.config.UploadProperties;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ProductMapper;
import fr.gopartner.tregusto.administration.infrastructure.persistence.CategoryRepository;
import fr.gopartner.tregusto.administration.internal.ProductService;
import fr.gopartner.tregusto.administration.utils.ImageStorageUtil;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import fr.gopartner.tregusto.common.utils.FileUtils;
import fr.gopartner.tregusto.common.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductApiImpl implements ProductsApi, ApiV1Administration {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UploadProperties uploadProperties;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<ProductDTO>> listProducts(Integer categoryId, String status, Boolean available) {
        var products = productService.listProducts(categoryId, status, available);
        return ResponseEntity.ok(productMapper.toDtoList(products));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        var products = productService.getFeaturedProducts();
        return ResponseEntity.ok(productMapper.toDtoList(products));
    }

    @Override
    public ResponseEntity<ProductDTO> getProductBySlug(String slug) {
        var product = productService.getBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var dto = productMapper.toDto(product);

        if (dto.getImages() != null) {
            for (var imageDto : dto.getImages()) {
                if (imageDto.getImageUrl() != null) {
                    String base64Image = ImageUtils.fileToBase64(
                        uploadProperties.getBaseDir(),
                        imageDto.getImageUrl(),
                        uploadProperties.getMaxFileSize()
                    );
                    imageDto.setImageUrl(base64Image);
                }
            }
        }

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<ProductDTO> createProduct(String name, Double price, Integer categoryId, List<MultipartFile> images, String shortDescription, String longDescription, String status, Boolean isAvailable) {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName(name);
        request.setPrice(price);
        request.setCategoryId(categoryId);
        request.setShortDescription(shortDescription);
        request.setLongDescription(longDescription);
        request.setIsAvailable(isAvailable != null ? isAvailable : true);

        var entity = productMapper.toEntity(request);
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        entity.setCategory(category);

        if (status != null) {
            entity.setStatus(ProductStatus.valueOf(status.toUpperCase()));
        }

        var created = productService.create(entity);

        if (images != null && !images.isEmpty()) {
            try {
                for (int i = 0; i < images.size(); i++) {
                    MultipartFile image = images.get(i);
                    String imageUrl = FileUtils.saveFile(
                            uploadProperties.getBaseDir(),
                            category.getSlug() + "/" + created.getId(),
                            created.getId().toString(),
                            image,
                            uploadProperties.getMaxFileSize(),
                            uploadProperties.getAllowedExtensions()
                    );

                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setDisplayOrder(i);
                    productImage.setProduct(created);
                    created.getImages().add(productImage);
                }
                productService.update(created.getId(), created);
            } catch (IOException e) {
                FileUtils.deleteDirectory(uploadProperties.getBaseDir(), "products/" + created.getId());
                throw new RuntimeException("Failed to save product images", e);
            }
        }

        return ResponseEntity.status(201).body(productMapper.toDto(created));
    }

    @Override
    public ResponseEntity<ProductDTO> updateProduct(Integer id, String name, Double price, Integer categoryId, String shortDescription, String longDescription, String status, Boolean isAvailable, List<MultipartFile> images) {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setName(name);
        request.setPrice(price);
        request.setCategoryId(categoryId);
        request.setShortDescription(shortDescription);
        request.setLongDescription(longDescription);
        request.setIsAvailable(isAvailable != null ? isAvailable : true);

        var entity = productMapper.toEntity(request);
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        entity.setCategory(category);

        if (status != null) {
            entity.setStatus(ProductStatus.valueOf(status.toUpperCase()));
        }

        var updated = productService.update(id, entity);

        if (images != null && !images.isEmpty()) {
            try {
                ImageStorageUtil.deleteAllProductImages(uploadProperties.getBaseDir(), id);
                updated.getImages().clear();

                for (int i = 0; i < images.size(); i++) {
                    MultipartFile image = images.get(i);
                    String imageUrl = FileUtils.saveFile(
                            uploadProperties.getBaseDir(),
                            "products/" + updated.getId(),
                            updated.getId() + "-" + i,
                            image,
                            uploadProperties.getMaxFileSize(),
                            uploadProperties.getAllowedExtensions()
                    );

                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setDisplayOrder(i);
                    productImage.setProduct(updated);
                    updated.getImages().add(productImage);
                }
                productService.update(id, updated);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save product images", e);
            }
        }

        return ResponseEntity.ok(productMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        productService.deactivate(id);
        ImageStorageUtil.deleteAllProductImages(uploadProperties.getBaseDir(), id);
        return ResponseEntity.noContent().build();
    }
}

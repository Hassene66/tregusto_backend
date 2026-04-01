package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ProductsApi;
import fr.gopartner.tregusto.administration.api.generated.ProductDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductRequestDTO;
import fr.gopartner.tregusto.administration.domain.Category;
import fr.gopartner.tregusto.administration.domain.Product;
import fr.gopartner.tregusto.administration.domain.ProductStatus;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ProductMapper;
import fr.gopartner.tregusto.administration.internal.ProductService;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductApiImpl implements ProductsApi, ApiV1Administration {

    private final ProductService productService;
    private final ProductMapper productMapper;

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
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @Override
    public ResponseEntity<ProductDTO> createProduct(ProductRequestDTO request) {
        var entity = productMapper.toEntity(request);
        var category = new Category();
        category.setId(request.getCategoryId());
        entity.setCategory(category);

        if (request.getStatus() != null) {
            entity.setStatus(ProductStatus.valueOf(request.getStatus().name()));
        }

        var created = productService.create(entity);
        return ResponseEntity.status(201).body(productMapper.toDto(created));
    }

    @Override
    public ResponseEntity<ProductDTO> updateProduct(Integer id, ProductRequestDTO request) {
        var entity = productMapper.toEntity(request);
        var category = new Category();
        category.setId(request.getCategoryId());
        entity.setCategory(category);

        if (request.getStatus() != null) {
            entity.setStatus(ProductStatus.valueOf(request.getStatus().name()));
        }

        var updated = productService.update(id, entity);
        return ResponseEntity.ok(productMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        productService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}

package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ProductImagesApi;
import fr.gopartner.tregusto.administration.api.generated.ProductImageDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductImageRequestDTO;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ProductImageMapper;
import fr.gopartner.tregusto.administration.internal.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductImageApiImpl implements ProductImagesApi, ApiV1Administration {

    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;

    @Override
    public ResponseEntity<ProductImageDTO> addProductImage(Integer productId, ProductImageRequestDTO request) {
        var entity = productImageMapper.toEntity(request);
        var created = productImageService.add(productId, entity);
        return ResponseEntity.status(201).body(productImageMapper.toDto(created));
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

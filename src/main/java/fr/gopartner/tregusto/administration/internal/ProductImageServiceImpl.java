package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.ProductImage;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductImageRepository;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductImage> getByProductId(Integer productId) {
        return productImageRepository.findByProductIdOrderByDisplayOrderAsc(productId);
    }

    @Override
    @Transactional
    public ProductImage add(Integer productId, ProductImage image) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        image.setProduct(product);
        return productImageRepository.save(image);
    }

    @Override
    @Transactional
    public ProductImage update(Integer productId, Integer imageId, ProductImage image) {
        ProductImage existing = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Product image not found"));

        existing.setImageUrl(image.getImageUrl());
        existing.setAltText(image.getAltText());
        existing.setDisplayOrder(image.getDisplayOrder());

        return productImageRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Integer productId, Integer imageId) {
        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Product image not found"));

        productImageRepository.delete(image);
    }
}

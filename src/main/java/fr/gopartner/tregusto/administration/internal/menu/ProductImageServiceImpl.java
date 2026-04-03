package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.ProductImage;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductImageRepository;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductRepository;
import fr.gopartner.tregusto.common.config.ImageUploadConfig;
import fr.gopartner.tregusto.common.exception.shared.FileSystemException;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import fr.gopartner.tregusto.common.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ImageUploadConfig imageUploadConfig;

    @Override
    public List<ProductImage> getByProductId(Integer productId) {
        return productImageRepository.findByProductIdOrderByDisplayOrderAsc(productId);
    }

    @Override
    public Integer getMaxDisplayOrder(Integer productId) {
        return productImageRepository.findMaxDisplayOrderByProductId(productId);
    }

    @Override
    @Transactional
    public ProductImage add(Integer productId, ProductImage image) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (image.getDisplayOrder() == null) {
            Integer maxOrder = productImageRepository.findMaxDisplayOrderByProductId(productId);
            image.setDisplayOrder(maxOrder + 1);
        }

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

    @Override
    @Transactional
    public ProductImage updateWithImage(Integer productId, Integer imageId, MultipartFile image, String altText, Integer displayOrder) throws IOException {
        ProductImage existing = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Product image not found"));

        String newImageUrl = existing.getImageUrl();

        if (image != null && !image.isEmpty()) {
            FileUtils.deleteFile(imageUploadConfig.getBaseDir(), existing.getImageUrl());

            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            newImageUrl = FileUtils.saveFile(
                    imageUploadConfig.getBaseDir(),
                    product.getCategory().getSlug() + "/" + product.getId(),
                    productId.toString(),
                    image,
                    imageUploadConfig.getMaxFileSize(),
                    imageUploadConfig.getAllowedExtensions()
            );
        }

        existing.setImageUrl(newImageUrl);
        if (altText != null) {
            existing.setAltText(altText);
        }
        if (displayOrder != null) {
            existing.setDisplayOrder(displayOrder);
        }

        return productImageRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteWithFile(Integer productId, Integer imageId) {
        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Product image not found"));

        String imageUrl = image.getImageUrl();

        productImageRepository.delete(image);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            boolean deleted = FileUtils.deleteFile(imageUploadConfig.getBaseDir(), imageUrl);
            if (!deleted) {
                throw new FileSystemException("Failed to delete image file: " + imageUrl);
            }
        }
    }
}

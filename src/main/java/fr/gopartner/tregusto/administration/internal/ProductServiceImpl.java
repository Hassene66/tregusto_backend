package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.Product;
import fr.gopartner.tregusto.administration.domain.ProductStatus;
import fr.gopartner.tregusto.administration.infrastructure.persistence.CategoryRepository;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import fr.gopartner.tregusto.common.utils.SlugGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> listProducts(Integer categoryId, String status, Boolean available) {
        if (categoryId != null) {
            return productRepository.findByCategoryIdAndIsAvailableTrue(categoryId);
        }
        if (status != null) {
            return productRepository.findByStatusInAndIsAvailableTrue(List.of(ProductStatus.valueOf(status.toUpperCase())));
        }
        if (Boolean.FALSE.equals(available)) {
            return productRepository.findAll();
        }
        return productRepository.findByIsAvailableTrue();
    }

    @Override
    public List<Product> getFeaturedProducts() {
        return productRepository.findByStatusInAndIsAvailableTrue(List.of(ProductStatus.NEW, ProductStatus.POPULAR));
    }

    @Override
    public Optional<Product> getBySlug(String slug) {
        return productRepository.findBySlugWithDetails(slug);
    }

    @Override
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product create(Product product) {
        String slug = product.getSlug();

        if (slug == null || slug.isEmpty()) {
            slug = SlugGenerator.generate(product.getName());
        }

        int counter = 1;
        String originalSlug = slug;
        while (productRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + counter++;
        }

        product.setSlug(slug);

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Integer id, Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existing.setName(product.getName());
        existing.setShortDescription(product.getShortDescription());
        existing.setLongDescription(product.getLongDescription());
        existing.setPrice(product.getPrice());
        existing.setStatus(product.getStatus());
        existing.setIsAvailable(product.getIsAvailable());

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            var category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existing.setCategory(category);
        }

        return productRepository.save(existing);
    }

    @Override
    @Transactional
    public void deactivate(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setIsAvailable(false);
        productRepository.save(product);
    }
}

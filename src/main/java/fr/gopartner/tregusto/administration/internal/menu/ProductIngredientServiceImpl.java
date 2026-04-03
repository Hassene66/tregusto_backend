package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.Ingredient;
import fr.gopartner.tregusto.administration.domain.menu.Product;
import fr.gopartner.tregusto.administration.domain.menu.ProductIngredient;
import fr.gopartner.tregusto.administration.domain.menu.ProductIngredient.ProductIngredientId;
import fr.gopartner.tregusto.administration.infrastructure.persistence.IngredientRepository;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductIngredientRepository;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ProductRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductIngredientServiceImpl implements ProductIngredientService {

    private final ProductIngredientRepository productIngredientRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public List<ProductIngredient> getByProductId(Integer productId) {
        return productIngredientRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public ProductIngredient add(Integer productId, ProductIngredient productIngredient) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Ingredient ingredient = ingredientRepository.findById(productIngredient.getIngredient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        ProductIngredientId id = new ProductIngredientId();
        id.setProductId(productId);
        id.setIngredientId(ingredient.getId());

        productIngredient.setId(id);
        productIngredient.setProduct(product);
        productIngredient.setIngredient(ingredient);

        return productIngredientRepository.save(productIngredient);
    }

    @Override
    @Transactional
    public ProductIngredient update(Integer productId, Integer ingredientId, ProductIngredient productIngredient) {
        ProductIngredientId id = new ProductIngredientId();
        id.setProductId(productId);
        id.setIngredientId(ingredientId);

        ProductIngredient existing = productIngredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product ingredient not found"));

        existing.setQuantity(productIngredient.getQuantity());
        existing.setDisplayOrder(productIngredient.getDisplayOrder());
        existing.setIsOptional(productIngredient.getIsOptional());

        return productIngredientRepository.save(existing);
    }

    @Override
    @Transactional
    public void remove(Integer productId, Integer ingredientId) {
        ProductIngredientId id = new ProductIngredientId();
        id.setProductId(productId);
        id.setIngredientId(ingredientId);

        if (!productIngredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product ingredient not found");
        }

        productIngredientRepository.deleteById(id);
    }
}

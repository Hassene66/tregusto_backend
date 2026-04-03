package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ProductIngredientsApi;
import fr.gopartner.tregusto.administration.api.generated.ProductIngredientDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductIngredientRequestDTO;
import fr.gopartner.tregusto.administration.domain.menu.Ingredient;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ProductIngredientMapper;
import fr.gopartner.tregusto.administration.internal.menu.ProductIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductIngredientApiImpl implements ProductIngredientsApi, ApiV1Administration {

    private final ProductIngredientService productIngredientService;
    private final ProductIngredientMapper productIngredientMapper;

    @Override
    public ResponseEntity<List<ProductIngredientDTO>> getProductIngredients(Integer productId) {
        var ingredients = productIngredientService.getByProductId(productId);
        return ResponseEntity.ok(productIngredientMapper.toDtoList(ingredients));
    }

    @Override
    public ResponseEntity<ProductIngredientDTO> addIngredientToProduct(Integer productId, ProductIngredientRequestDTO request) {
        var entity = productIngredientMapper.toEntity(request);
        var ingredient = new Ingredient();
        ingredient.setId(request.getIngredientId());
        entity.setIngredient(ingredient);

        var created = productIngredientService.add(productId, entity);
        return ResponseEntity.status(201).body(productIngredientMapper.toDto(created));
    }

    @Override
    public ResponseEntity<ProductIngredientDTO> updateProductIngredient(Integer productId, Integer ingredientId, ProductIngredientRequestDTO request) {
        var entity = productIngredientMapper.toEntity(request);
        var updated = productIngredientService.update(productId, ingredientId, entity);
        return ResponseEntity.ok(productIngredientMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> removeIngredientFromProduct(Integer productId, Integer ingredientId) {
        productIngredientService.remove(productId, ingredientId);
        return ResponseEntity.noContent().build();
    }
}

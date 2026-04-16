package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.IngredientsApi;
import fr.gopartner.tregusto.administration.api.generated.IngredientDTO;
import fr.gopartner.tregusto.administration.api.generated.IngredientRequestDTO;
import fr.gopartner.tregusto.administration.infrastructure.mapper.IngredientMapper;
import fr.gopartner.tregusto.administration.internal.menu.IngredientService;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngredientApiImpl implements IngredientsApi, ApiV1Administration {

    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    @Override
    public ResponseEntity<List<IngredientDTO>> listIngredients() {
        var ingredients = ingredientService.listAll();
        return ResponseEntity.ok(ingredientMapper.toDtoList(ingredients));
    }

    @Override
    public ResponseEntity<IngredientDTO> getIngredientBySlug(String slug) {
        var ingredient = ingredientService.getBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        return ResponseEntity.ok(ingredientMapper.toDto(ingredient));
    }

    @Override
    public ResponseEntity<IngredientDTO> createIngredient(IngredientRequestDTO request) {
        var entity = ingredientMapper.toEntity(request);
        var created = ingredientService.create(entity);
        return ResponseEntity.status(201).body(ingredientMapper.toDto(created));
    }

    @Override
    public ResponseEntity<IngredientDTO> updateIngredient(Integer id, IngredientRequestDTO request) {
        var entity = ingredientMapper.toEntity(request);
        var updated = ingredientService.update(id, entity);
        return ResponseEntity.ok(ingredientMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> deleteIngredient(Integer id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

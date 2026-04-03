package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.IngredientCategoriesApi;
import fr.gopartner.tregusto.administration.api.generated.IngredientCategoryDTO;
import fr.gopartner.tregusto.administration.api.generated.IngredientCategoryRequestDTO;
import fr.gopartner.tregusto.administration.infrastructure.mapper.IngredientCategoryMapper;
import fr.gopartner.tregusto.administration.internal.menu.IngredientCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngredientCategoryApiImpl implements IngredientCategoriesApi, ApiV1Administration {

    private final IngredientCategoryService ingredientCategoryService;
    private final IngredientCategoryMapper ingredientCategoryMapper;

    @Override
    public ResponseEntity<List<IngredientCategoryDTO>> listIngredientCategories() {
        var categories = ingredientCategoryService.listAll();
        return ResponseEntity.ok(ingredientCategoryMapper.toDtoList(categories));
    }

    @Override
    public ResponseEntity<IngredientCategoryDTO> createIngredientCategory(IngredientCategoryRequestDTO request) {
        var entity = ingredientCategoryMapper.toEntity(request);
        var created = ingredientCategoryService.create(entity);
        return ResponseEntity.status(201).body(ingredientCategoryMapper.toDto(created));
    }

    @Override
    public ResponseEntity<IngredientCategoryDTO> updateIngredientCategory(Integer id, IngredientCategoryRequestDTO request) {
        var entity = ingredientCategoryMapper.toEntity(request);
        var updated = ingredientCategoryService.update(id, entity);
        return ResponseEntity.ok(ingredientCategoryMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> deleteIngredientCategory(Integer id) {
        ingredientCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

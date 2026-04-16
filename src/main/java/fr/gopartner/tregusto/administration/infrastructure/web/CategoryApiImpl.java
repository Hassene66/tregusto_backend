package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.CategoriesApi;
import fr.gopartner.tregusto.administration.api.generated.CategoryDTO;
import fr.gopartner.tregusto.administration.api.generated.CategoryRequestDTO;
import fr.gopartner.tregusto.administration.infrastructure.mapper.CategoryMapper;
import fr.gopartner.tregusto.administration.internal.menu.CategoryService;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiImpl implements CategoriesApi, ApiV1Administration {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<List<CategoryDTO>> listCategories() {
        var categories = categoryService.listActiveCategories();
        return ResponseEntity.ok(categoryMapper.toDtoList(categories));
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryBySlug(String slug) {
        var category = categoryService.getBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @Override
    public ResponseEntity<CategoryDTO> createCategory(CategoryRequestDTO request) {
        var entity = categoryMapper.toEntity(request);
        var created = categoryService.create(entity);
        return ResponseEntity.status(201).body(categoryMapper.toDto(created));
    }

    @Override
    public ResponseEntity<CategoryDTO> updateCategory(Integer id, CategoryRequestDTO request) {
        var entity = categoryMapper.toEntity(request);
        var updated = categoryService.update(id, entity);
        return ResponseEntity.ok(categoryMapper.toDto(updated));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Integer id) {
        categoryService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}

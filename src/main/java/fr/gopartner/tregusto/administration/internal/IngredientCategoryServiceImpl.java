package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.IngredientCategory;
import fr.gopartner.tregusto.administration.infrastructure.persistence.IngredientCategoryRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceAlreadyExistsException;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientCategoryServiceImpl implements IngredientCategoryService {

    private final IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public List<IngredientCategory> listAll() {
        return ingredientCategoryRepository.findAll();
    }

    @Override
    public Optional<IngredientCategory> getById(Integer id) {
        return ingredientCategoryRepository.findById(id);
    }

    @Override
    @Transactional
    public IngredientCategory create(IngredientCategory category) {
        if (ingredientCategoryRepository.existsByName(category.getName())) {
            throw new ResourceAlreadyExistsException("Ingredient category already exists");
        }
        return ingredientCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public IngredientCategory update(Integer id, IngredientCategory category) {
        IngredientCategory existing = ingredientCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient category not found"));

        existing.setName(category.getName());

        return ingredientCategoryRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!ingredientCategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient category not found");
        }
        ingredientCategoryRepository.deleteById(id);
    }
}

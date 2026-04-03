package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.Ingredient;
import fr.gopartner.tregusto.administration.infrastructure.persistence.IngredientCategoryRepository;
import fr.gopartner.tregusto.administration.infrastructure.persistence.IngredientRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceAlreadyExistsException;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public List<Ingredient> listAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> getBySlug(String slug) {
        return ingredientRepository.findBySlug(slug);
    }

    @Override
    @Transactional
    public Ingredient create(Ingredient ingredient) {
        if (ingredientRepository.existsBySlug(ingredient.getSlug())) {
            throw new ResourceAlreadyExistsException("Ingredient with slug already exists");
        }
        return ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional
    public Ingredient update(Integer id, Ingredient ingredient) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        existing.setName(ingredient.getName());

        if (ingredient.getCategory() != null && ingredient.getCategory().getId() != null) {
            var category = ingredientCategoryRepository.findById(ingredient.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient category not found"));
            existing.setCategory(category);
        }

        return ingredientRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient not found");
        }
        ingredientRepository.deleteById(id);
    }
}

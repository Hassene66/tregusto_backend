package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.Category;
import fr.gopartner.tregusto.administration.infrastructure.persistence.CategoryRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceAlreadyExistsException;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    @Override
    public Optional<Category> getBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    @Override
    @Transactional
    public Category create(Category category) {
        if (categoryRepository.existsBySlug(category.getSlug())) {
            throw new ResourceAlreadyExistsException("Category with slug already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category update(Integer id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existing.setName(category.getName());
        existing.setImageUrl(category.getImageUrl());

        return categoryRepository.save(existing);
    }

    @Override
    @Transactional
    public void deactivate(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setIsActive(false);
        categoryRepository.save(category);
    }
}

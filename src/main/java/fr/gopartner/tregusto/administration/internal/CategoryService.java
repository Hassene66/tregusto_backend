package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> listActiveCategories();

    Optional<Category> getBySlug(String slug);

    Category create(Category category);

    Category update(Integer id, Category category);

    void deactivate(Integer id);
}

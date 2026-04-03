package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.IngredientCategory;

import java.util.List;
import java.util.Optional;

public interface IngredientCategoryService {

    List<IngredientCategory> listAll();

    Optional<IngredientCategory> getById(Integer id);

    IngredientCategory create(IngredientCategory category);

    IngredientCategory update(Integer id, IngredientCategory category);

    void delete(Integer id);
}

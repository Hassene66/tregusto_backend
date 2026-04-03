package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    List<Ingredient> listAll();

    Optional<Ingredient> getBySlug(String slug);

    Ingredient create(Ingredient ingredient);

    Ingredient update(Integer id, Ingredient ingredient);

    void delete(Integer id);
}

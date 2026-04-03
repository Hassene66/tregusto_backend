package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.ProductIngredient;

import java.util.List;

public interface ProductIngredientService {

    List<ProductIngredient> getByProductId(Integer productId);

    ProductIngredient add(Integer productId, ProductIngredient productIngredient);

    ProductIngredient update(Integer productId, Integer ingredientId, ProductIngredient productIngredient);

    void remove(Integer productId, Integer ingredientId);
}

package fr.gopartner.tregusto.administration.infrastructure.persistence;

import fr.gopartner.tregusto.administration.domain.menu.ProductIngredient;
import fr.gopartner.tregusto.administration.domain.menu.ProductIngredient.ProductIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, ProductIngredientId> {

    @Query("SELECT pi FROM ProductIngredient pi JOIN FETCH pi.ingredient WHERE pi.product.id = :productId ORDER BY pi.displayOrder ASC")
    List<ProductIngredient> findByProductId(@Param("productId") Integer productId);

    Optional<ProductIngredient> findByProductIdAndIngredientId(Integer productId, Integer ingredientId);

    boolean existsByProductIdAndIngredientId(Integer productId, Integer ingredientId);
}

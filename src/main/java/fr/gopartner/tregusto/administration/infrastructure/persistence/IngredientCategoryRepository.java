package fr.gopartner.tregusto.administration.infrastructure.persistence;

import fr.gopartner.tregusto.administration.domain.menu.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Integer> {

    List<IngredientCategory> findAll();

    Optional<IngredientCategory> findByName(String name);

    boolean existsByName(String name);
}

package fr.gopartner.tregusto.administration.infrastructure.persistence;

import fr.gopartner.tregusto.administration.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByIsActiveTrue();

    Optional<Category> findBySlug(String slug);

    boolean existsBySlug(String slug);
}

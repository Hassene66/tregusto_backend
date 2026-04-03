package fr.gopartner.tregusto.administration.infrastructure.persistence;

import fr.gopartner.tregusto.administration.domain.menu.Product;
import fr.gopartner.tregusto.administration.domain.menu.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByIsAvailableTrue();

    List<Product> findByCategoryIdAndIsAvailableTrue(Integer categoryId);

    List<Product> findByStatusInAndIsAvailableTrue(List<ProductStatus> statuses);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.ingredients WHERE p.slug = :slug AND p.isAvailable = true")
    Optional<Product> findBySlugWithDetails(@Param("slug") String slug);

    Optional<Product> findBySlugAndIsAvailableTrue(String slug);

    boolean existsBySlug(String slug);
}

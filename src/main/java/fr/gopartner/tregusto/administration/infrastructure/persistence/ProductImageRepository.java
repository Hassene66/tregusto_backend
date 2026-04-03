package fr.gopartner.tregusto.administration.infrastructure.persistence;

import fr.gopartner.tregusto.administration.domain.menu.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Integer productId);


    @Query("SELECT MAX(pi.displayOrder) FROM ProductImage pi WHERE pi.product.id = :productId")
    Integer findMaxDisplayOrderByProductId(@Param("productId") Integer productId);

    void deleteByProductId(Integer productId);

    List<ProductImage> findByProductId(Integer productId);
}

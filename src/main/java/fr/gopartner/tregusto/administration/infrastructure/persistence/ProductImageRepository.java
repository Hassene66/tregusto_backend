package fr.gopartner.tregusto.administration.infrastructure.persistence;

import fr.gopartner.tregusto.administration.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Integer productId);

    Integer findMaxDisplayOrderByProductId(Integer productId);

    void deleteByProductId(Integer productId);

    List<ProductImage> findByProductId(Integer productId);
}

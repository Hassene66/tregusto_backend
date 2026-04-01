package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> listProducts(Integer categoryId, String status, Boolean available);

    List<Product> getFeaturedProducts();

    Optional<Product> getBySlug(String slug);

    Product create(Product product);

    Product update(Integer id, Product product);

    void deactivate(Integer id);
}

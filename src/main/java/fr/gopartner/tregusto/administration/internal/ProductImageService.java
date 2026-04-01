package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.ProductImage;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> getByProductId(Integer productId);

    ProductImage add(Integer productId, ProductImage image);

    ProductImage update(Integer productId, Integer imageId, ProductImage image);

    void delete(Integer productId, Integer imageId);
}

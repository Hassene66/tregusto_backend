package fr.gopartner.tregusto.administration.internal.menu;

import fr.gopartner.tregusto.administration.domain.menu.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {

    List<ProductImage> getByProductId(Integer productId);

    Integer getMaxDisplayOrder(Integer productId);

    ProductImage add(Integer productId, ProductImage image);

    ProductImage update(Integer productId, Integer imageId, ProductImage image);

    void delete(Integer productId, Integer imageId);

    ProductImage updateWithImage(Integer productId, Integer imageId, MultipartFile image, String altText, Integer displayOrder) throws IOException;

    void deleteWithFile(Integer productId, Integer imageId);
}

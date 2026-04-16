package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.ProductDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductRequestDTO;
import fr.gopartner.tregusto.administration.domain.menu.Product;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class, uses = {ProductImageMapper.class, ProductIngredientMapper.class, CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductDTO toDto(Product entity);

    List<ProductDTO> toDtoList(List<Product> entities);
}

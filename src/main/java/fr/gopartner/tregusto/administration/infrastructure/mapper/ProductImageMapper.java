package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.ProductImageDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductImageRequestDTO;
import fr.gopartner.tregusto.administration.domain.menu.ProductImage;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface ProductImageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductImage toEntity(ProductImageRequestDTO dto);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductImageDTO toDto(ProductImage entity);

    List<ProductImageDTO> toDtoList(List<ProductImage> entities);
}

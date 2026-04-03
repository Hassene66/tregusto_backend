package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.ProductIngredientDTO;
import fr.gopartner.tregusto.administration.api.generated.ProductIngredientRequestDTO;
import fr.gopartner.tregusto.administration.domain.menu.ProductIngredient;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface ProductIngredientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    ProductIngredient toEntity(ProductIngredientRequestDTO dto);

    @Mapping(target = "ingredientName", source = "ingredient.name")
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "ingredientId", ignore = true)
    ProductIngredientDTO toDto(ProductIngredient entity);

    List<ProductIngredientDTO> toDtoList(List<ProductIngredient> entities);
}

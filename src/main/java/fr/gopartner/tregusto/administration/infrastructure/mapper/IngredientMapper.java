package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.IngredientDTO;
import fr.gopartner.tregusto.administration.api.generated.IngredientRequestDTO;
import fr.gopartner.tregusto.administration.domain.Ingredient;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface IngredientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Ingredient toEntity(IngredientRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "createdAt", ignore = true)
    IngredientDTO toDto(Ingredient entity);

    List<IngredientDTO> toDtoList(List<Ingredient> entities);
}

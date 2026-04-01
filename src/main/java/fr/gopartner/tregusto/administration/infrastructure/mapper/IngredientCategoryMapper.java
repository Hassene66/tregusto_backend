package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.IngredientCategoryDTO;
import fr.gopartner.tregusto.administration.api.generated.IngredientCategoryRequestDTO;
import fr.gopartner.tregusto.administration.domain.IngredientCategory;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface IngredientCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    IngredientCategory toEntity(IngredientCategoryRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    IngredientCategoryDTO toDto(IngredientCategory entity);

    List<IngredientCategoryDTO> toDtoList(List<IngredientCategory> entities);
}

package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.CategoryDTO;
import fr.gopartner.tregusto.administration.api.generated.CategoryRequestDTO;
import fr.gopartner.tregusto.administration.domain.Category;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category toEntity(CategoryRequestDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    CategoryDTO toDto(Category entity);

    List<CategoryDTO> toDtoList(List<Category> entities);
}

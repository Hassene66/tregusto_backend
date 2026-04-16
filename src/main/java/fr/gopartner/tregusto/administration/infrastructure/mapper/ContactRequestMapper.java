package fr.gopartner.tregusto.administration.infrastructure.mapper;

import fr.gopartner.tregusto.administration.api.generated.ContactRequestDTO;
import fr.gopartner.tregusto.administration.domain.contact.ContactRequest;
import fr.gopartner.tregusto.common.GlobalMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface ContactRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(fr.gopartner.tregusto.administration.domain.contact.ContactRequestStatus.PENDING)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ContactRequest toEntity(ContactRequestDTO contactRequestDTO);
}

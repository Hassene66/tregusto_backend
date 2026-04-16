package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ContactApi;
import fr.gopartner.tregusto.administration.api.generated.ContactRequestDTO;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ContactRequestMapper;
import fr.gopartner.tregusto.administration.internal.contact.ContactRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactApiImpl implements ContactApi, ApiV1Administration {

    private final ContactRequestService contactRequestService;
    private final ContactRequestMapper contactRequestMapper;

    @Override
    public ResponseEntity<Void> createContactRequest(ContactRequestDTO contactRequestDTO) {

        contactRequestService.createContactRequest(contactRequestMapper.toEntity(contactRequestDTO), contactRequestDTO.getCaptcha());

        return ResponseEntity.ok().build();
    }
}

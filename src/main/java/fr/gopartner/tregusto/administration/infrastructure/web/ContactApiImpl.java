package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ContactApi;
import fr.gopartner.tregusto.administration.api.generated.ContactRequestDTO;
import fr.gopartner.tregusto.administration.infrastructure.mapper.ContactRequestMapper;
import fr.gopartner.tregusto.administration.internal.ContactRequestService;
import fr.gopartner.tregusto.administration.spi.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactApiImpl implements ContactApi, ApiV1Contact {

    private final CaptchaService captchaService;
    private final ContactRequestService contactRequestService;
    private final ContactRequestMapper contactRequestMapper;

    @Override
    public ResponseEntity<Void> createContactRequest(ContactRequestDTO contactRequestDTO) {
//        if (!captchaService.verifyCaptcha(contactRequestDTO.getCaptcha())) {
//            return ResponseEntity.badRequest().build();
//        }

        //TODO: save contact request in database if captcha is valid
        contactRequestService.createContactRequest(contactRequestMapper.toEntity(contactRequestDTO));

        //TODO: send email to support team
        return ContactApi.super.createContactRequest(contactRequestDTO);
    }
}

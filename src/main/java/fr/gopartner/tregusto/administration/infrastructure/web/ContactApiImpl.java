package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.ContactApi;
import fr.gopartner.tregusto.administration.api.generated.ContactRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactApiImpl implements ContactApi, ApiV1Contact {


    @Override
    public ResponseEntity<Void> createContactRequest(ContactRequestDTO contactRequestDTO) {

        //TODO: verify captcha

        //TODO: save contact request in database if captcha is valid

        //TODO: send email to support team
        return ContactApi.super.createContactRequest(contactRequestDTO);
    }
}

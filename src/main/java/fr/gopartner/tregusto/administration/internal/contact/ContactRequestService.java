package fr.gopartner.tregusto.administration.internal.contact;

import fr.gopartner.tregusto.administration.domain.contact.ContactRequest;

public interface ContactRequestService {
    void createContactRequest(ContactRequest contactRequest, String captcha);
}

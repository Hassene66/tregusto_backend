package fr.gopartner.tregusto.administration.api.events;

import fr.gopartner.tregusto.administration.domain.ContactRequest;
import org.springframework.context.ApplicationEvent;

public class ContactRequestSubmittedEvent extends ApplicationEvent {

    private final ContactRequest contactRequest;

    public ContactRequestSubmittedEvent(Object source, ContactRequest contactRequest) {
        super(source);
        this.contactRequest = contactRequest;
    }

    public ContactRequest getSupportRequest() {
        return contactRequest;
    }
}

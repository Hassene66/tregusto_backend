package fr.gopartner.tregusto.notification.api.events.listeners;

import fr.gopartner.tregusto.administration.api.events.ContactRequestCreatedEvent;
import fr.gopartner.tregusto.notification.internal.EmailNotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdministrationEventsListener {

    private final EmailNotificationService emailNotificationService;


    @ApplicationModuleListener
    void handleContactFormSubmissionEvent(ContactRequestCreatedEvent event) throws MessagingException {
        log.error("Contact form submitted: {}", event);
        emailNotificationService.sendContactConfirmation(event.requestId(), event.recipientName(), event.recipientEmail(), event.message());
    }

}

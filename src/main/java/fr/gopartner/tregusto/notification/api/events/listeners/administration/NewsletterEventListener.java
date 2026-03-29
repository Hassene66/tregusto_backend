package fr.gopartner.tregusto.notification.api.events.listeners.administration;

import fr.gopartner.tregusto.administration.api.events.NewsletterSubscriptionCreatedEvent;
import fr.gopartner.tregusto.notification.internal.EmailNotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsletterEventListener {

    private final EmailNotificationService emailNotificationService;

    @ApplicationModuleListener
    void handleNewsletterSubscriptionCreated(NewsletterSubscriptionCreatedEvent event) {
        log.info("Sending newsletter confirmation email for: {}", event.email());
        try {
            emailNotificationService.sendNewsletterConfirmation(event.email(), event.token());
        } catch (MessagingException e) {
            log.error("Failed to send newsletter confirmation email to {}", event.email(), e);
        }
    }
}

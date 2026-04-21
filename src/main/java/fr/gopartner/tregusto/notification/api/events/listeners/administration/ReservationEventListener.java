package fr.gopartner.tregusto.notification.api.events.listeners.administration;

import fr.gopartner.tregusto.notification.internal.EmailNotificationService;
import fr.gopartner.tregusto.reservation.api.events.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final EmailNotificationService emailNotificationService;

    @ApplicationModuleListener
    void handleReservationCreated(ReservationCreatedEvent event) {
        log.info("Sending reservation confirmation email for: {}", event.recipientEmail());
        try {
            emailNotificationService.sendReservationConfirmation(
                    event.recipientEmail(),
                    event.recipientName(),
                    event.reservationId(),
                    event.reservationDate().toString(),
                    event.reservationTime().toString(),
                    event.numberOfGuests(),
                    event.specialRequest()
            );

            emailNotificationService.sendReservationNotificationToManagers(
                    event.reservationId(),
                    event.reservationDate().toString(),
                    event.reservationTime().toString(),
                    event.numberOfGuests(),
                    event.recipientName(),
                    event.recipientEmail(),
                    event.phoneNumber(),
                    event.specialRequest()
            );
        } catch (Exception e) {
            log.error("Failed to send reservation emails for {}", event.recipientEmail(), e);
        }
    }
}

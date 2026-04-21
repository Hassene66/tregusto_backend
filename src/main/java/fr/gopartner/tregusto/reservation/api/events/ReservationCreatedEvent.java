package fr.gopartner.tregusto.reservation.api.events;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreatedEvent(
        String reservationId,
        String recipientName,
        String recipientEmail,
        String phoneNumber,
        LocalDate reservationDate,
        LocalTime reservationTime,
        int numberOfGuests,
        String specialRequest
) {
}

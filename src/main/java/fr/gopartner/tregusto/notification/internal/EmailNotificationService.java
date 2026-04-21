package fr.gopartner.tregusto.notification.internal;

import jakarta.mail.MessagingException;

public interface EmailNotificationService {

    void sendContactConfirmation(Long requestId, String recipientName, String recipientEmail, String message) throws MessagingException;

    void sendNewsletterConfirmation(String email, String token) throws MessagingException;

    void sendReservationConfirmation(String recipientEmail, String recipientName, String reservationId,
                                     String reservationDate, String reservationTime,
                                     int numberOfGuests, String specialRequest) throws MessagingException;

    void sendReservationNotificationToManagers(String reservationId, String reservationDate, String reservationTime,
                                               int numberOfGuests, String customerName, String customerEmail,
                                               String phoneNumber, String specialRequest) throws MessagingException;
}

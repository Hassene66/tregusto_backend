package fr.gopartner.tregusto.notification.internal;

import jakarta.mail.MessagingException;

public interface EmailNotificationService {

    void sendContactConfirmation(Long requestId, String recipientName, String recipientEmail, String message) throws MessagingException;
}

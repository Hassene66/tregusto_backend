package fr.gopartner.tregusto.administration.api.events;

public record ContactRequestCreatedEvent(Long requestId, String recipientName, String recipientEmail, String message) {
}

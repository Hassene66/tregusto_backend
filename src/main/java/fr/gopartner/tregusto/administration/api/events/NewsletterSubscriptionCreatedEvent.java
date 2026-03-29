package fr.gopartner.tregusto.administration.api.events;

public record NewsletterSubscriptionCreatedEvent(Long subscriptionId, String email, String token) {
}

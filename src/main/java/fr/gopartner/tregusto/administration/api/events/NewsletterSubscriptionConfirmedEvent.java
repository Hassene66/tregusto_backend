package fr.gopartner.tregusto.administration.api.events;

public record NewsletterSubscriptionConfirmedEvent(Long subscriptionId, String email) {
}

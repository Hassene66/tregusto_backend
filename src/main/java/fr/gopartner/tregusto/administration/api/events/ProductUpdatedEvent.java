package fr.gopartner.tregusto.administration.api.events;

public record ProductUpdatedEvent(Integer productId, String productName, String slug) {
}

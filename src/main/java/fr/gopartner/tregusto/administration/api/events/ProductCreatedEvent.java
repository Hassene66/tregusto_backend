package fr.gopartner.tregusto.administration.api.events;

public record ProductCreatedEvent(Integer productId, String productName, String slug, Integer categoryId) {
}

package fr.gopartner.tregusto.administration.internal.contact;

import fr.gopartner.tregusto.administration.domain.contact.NewsletterSubscription;

public interface NewsletterService {
    NewsletterSubscription subscribe(String email);

    void confirmSubscription(String token);

    void unsubscribe(String token);
}

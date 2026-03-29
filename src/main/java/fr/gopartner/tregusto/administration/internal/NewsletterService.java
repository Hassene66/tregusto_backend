package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.NewsletterSubscription;

public interface NewsletterService {
    NewsletterSubscription subscribe(String email);

    void confirmSubscription(String token);

    void unsubscribe(String token);
}

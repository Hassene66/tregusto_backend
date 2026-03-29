package fr.gopartner.tregusto.administration.infrastructure.web;

import fr.gopartner.tregusto.administration.api.generated.NewsletterApi;
import fr.gopartner.tregusto.administration.api.generated.NewsletterSubscribeRequestDTO;
import fr.gopartner.tregusto.administration.internal.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsletterApiImpl implements NewsletterApi, ApiV1Administration {

    private final NewsletterService newsletterService;

    @Override
    public ResponseEntity<Void> subscribeToNewsletter(NewsletterSubscribeRequestDTO newsletterSubscribeRequestDTO) {
        newsletterService.subscribe(newsletterSubscribeRequestDTO.getEmail());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> confirmNewsletterSubscription(String token) {
        newsletterService.confirmSubscription(token);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> unsubscribeFromNewsletter(String token) {
        newsletterService.unsubscribe(token);
        return ResponseEntity.ok().build();
    }
}

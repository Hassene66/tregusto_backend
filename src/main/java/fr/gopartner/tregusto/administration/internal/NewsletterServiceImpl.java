package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.api.events.NewsletterSubscriptionCreatedEvent;
import fr.gopartner.tregusto.administration.api.events.NewsletterSubscriptionConfirmedEvent;
import fr.gopartner.tregusto.administration.domain.NewsletterSubscription;
import fr.gopartner.tregusto.administration.infrastructure.persistence.NewsletterSubscriptionRepository;
import fr.gopartner.tregusto.common.exception.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterSubscriptionRepository repository;
    private final ApplicationEventPublisher events;

    @Override
    @Transactional
    public NewsletterSubscription subscribe(String email) {
        NewsletterSubscription subscription = repository.findByEmail(email)
                .map(sub -> handleExistingSubscription(sub, email))
                .orElseGet(() -> createNewSubscription(email));

        events.publishEvent(new NewsletterSubscriptionCreatedEvent(
                subscription.getId(),
                subscription.getEmail(),
                subscription.getConfirmationToken()
        ));

        return subscription;
    }

    private NewsletterSubscription handleExistingSubscription(NewsletterSubscription subscription, String email) {
        if (subscription.getStatus() == NewsletterSubscription.SubscriptionStatus.ACTIVE) {
            log.info("Email already actively subscribed: {}", email);
            return subscription;
        }

        log.info("Re-subscribing user with status {}: {}", subscription.getStatus(), email);

        String newToken = UUID.randomUUID().toString();
        subscription.setStatus(NewsletterSubscription.SubscriptionStatus.PENDING);
        subscription.setConfirmationToken(newToken);
        subscription.setConfirmedAt(null);
        subscription.setUnsubscribedAt(null);
        subscription.setUpdatedAt(LocalDateTime.now());

        return repository.save(subscription);
    }

    private NewsletterSubscription createNewSubscription(String email) {
        String token = UUID.randomUUID().toString();

        NewsletterSubscription subscription = new NewsletterSubscription();
        subscription.setEmail(email);
        subscription.setStatus(NewsletterSubscription.SubscriptionStatus.PENDING);
        subscription.setConfirmationToken(token);
        subscription.setCreatedAt(LocalDateTime.now());

        return repository.save(subscription);
    }

    @Override
    @Transactional
    public void confirmSubscription(String token) {
        NewsletterSubscription subscription = repository
                .findByConfirmationToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired confirmation token"));

        if (subscription.getStatus() == NewsletterSubscription.SubscriptionStatus.ACTIVE) {
            log.info("Subscription already confirmed for email: {}", subscription.getEmail());
            return;
        }

        subscription.setStatus(NewsletterSubscription.SubscriptionStatus.ACTIVE);
        subscription.setConfirmedAt(LocalDateTime.now());
        repository.save(subscription);

        events.publishEvent(new NewsletterSubscriptionConfirmedEvent(
                subscription.getId(),
                subscription.getEmail()
        ));

        log.info("Newsletter subscription confirmed for email: {}", subscription.getEmail());
    }

    @Override
    @Transactional
    public void unsubscribe(String token) {
        NewsletterSubscription subscription = repository
                .findByConfirmationToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid unsubscribe link"));

        if (subscription.getStatus() == NewsletterSubscription.SubscriptionStatus.UNSUBSCRIBED) {
            log.info("Email already unsubscribed: {}", subscription.getEmail());
            return;
        }

        subscription.setStatus(NewsletterSubscription.SubscriptionStatus.UNSUBSCRIBED);
        subscription.setUnsubscribedAt(LocalDateTime.now());
        repository.save(subscription);

        log.info("Newsletter subscription unsubscribed for email: {}", subscription.getEmail());
    }
}

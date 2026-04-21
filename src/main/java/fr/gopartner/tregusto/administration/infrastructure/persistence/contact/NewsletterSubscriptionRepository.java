package fr.gopartner.tregusto.administration.infrastructure.persistence.contact;

import fr.gopartner.tregusto.administration.domain.contact.NewsletterSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsletterSubscriptionRepository extends JpaRepository<NewsletterSubscription, Long> {
    Optional<NewsletterSubscription> findByEmail(String email);
    Optional<NewsletterSubscription> findByConfirmationToken(String token);
    boolean existsByEmail(String email);
}

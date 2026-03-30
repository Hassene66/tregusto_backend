package fr.gopartner.tregusto.administration.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "newsletter_subscriptions")
public class NewsletterSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status = SubscriptionStatus.PENDING;

    @Column(name = "confirmation_token", nullable = false)
    private String confirmationToken;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "unsubscribed_at")
    private LocalDateTime unsubscribedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum SubscriptionStatus {
        PENDING,
        ACTIVE,
        UNSUBSCRIBED
    }
}

package fr.gopartner.tregusto.administration.infrastructure.exception;

import fr.gopartner.tregusto.common.exception.shared.InvalidArgumentsException;

public class NewsletterAlreadySubscribedException extends InvalidArgumentsException {
    public NewsletterAlreadySubscribedException() {
        super("Email already subscribed to newsletter");
    }
    
    public NewsletterAlreadySubscribedException(String message) {
        super(message);
    }
}

package fr.gopartner.tregusto.administration.internal.contact;

import fr.gopartner.tregusto.administration.api.events.ContactRequestCreatedEvent;
import fr.gopartner.tregusto.administration.domain.contact.ContactRequest;
import fr.gopartner.tregusto.administration.infrastructure.persistence.contact.ContactRequestRepository;
import fr.gopartner.tregusto.administration.spi.CaptchaService;
import fr.gopartner.tregusto.common.exception.shared.InvalidCaptchaException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactRequestServiceImpl implements ContactRequestService {

    private final ContactRequestRepository contactRequestRepository;
    private final CaptchaService captchaService;
    private final ApplicationEventPublisher events;

    @Override
    @Transactional
    public void createContactRequest(ContactRequest contactRequest, String captcha) {

        if (!captchaService.verifyCaptcha(captcha)) {
            throw new InvalidCaptchaException();
        }

        var savedRequest = contactRequestRepository.save(contactRequest);

        events.publishEvent(new ContactRequestCreatedEvent(savedRequest.getId(), contactRequest.getName(), contactRequest.getEmail(), contactRequest.getMessage()));

    }
}

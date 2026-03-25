package fr.gopartner.tregusto.administration.internal;

import fr.gopartner.tregusto.administration.domain.ContactRequest;
import fr.gopartner.tregusto.administration.infrastructure.persistence.ContactRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactRequestServiceImpl implements ContactRequestService {

    private final ContactRequestRepository contactRequestRepository;

    @Override
    public void createContactRequest(ContactRequest contactRequest) {
        contactRequestRepository.save(contactRequest);
    }
}

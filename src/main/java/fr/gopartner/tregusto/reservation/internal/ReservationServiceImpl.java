package fr.gopartner.tregusto.reservation.internal;

import fr.gopartner.tregusto.reservation.api.generated.ReservationRequestDTO;
import fr.gopartner.tregusto.reservation.api.generated.ReservationResponseDTO;
import fr.gopartner.tregusto.reservation.domain.Reservation;
 import fr.gopartner.tregusto.reservation.infrastructure.mapper.ReservationMapper;
import fr.gopartner.tregusto.reservation.infrastructure.persistence.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO) {
        log.info("Création d'une nouvelle réservation pour l'email: {}", requestDTO.getEmail());

         Reservation reservation = reservationMapper.toEntity(requestDTO);

         Reservation savedReservation = reservationRepository.save(reservation);

        log.info("Réservation créée avec succès - Email: {}, UUID: {} ,Status: {}",
                savedReservation.getEmail(), savedReservation.getUuid(),savedReservation.getStatut());

         return reservationMapper.toResponseDTO(savedReservation);
    }
}
package fr.gopartner.tregusto.reservation.infrastructure.web;

import fr.gopartner.tregusto.reservation.api.generated.ReservationRequestDTO;
import fr.gopartner.tregusto.reservation.api.generated.ReservationResponseDTO;
import fr.gopartner.tregusto.reservation.api.generated.RservationsApi;
import fr.gopartner.tregusto.reservation.internal.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReservationController implements  ApiV1Reservation,RservationsApi {
    private final ReservationService reservationService;

    @Override
    public ResponseEntity<ReservationResponseDTO> createReservation(ReservationRequestDTO reservationRequestDTO) {
        log.info("Création de réservation reçue pour l'email: {} ", reservationRequestDTO.getEmail());

        ReservationResponseDTO response = reservationService.createReservation(reservationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
package fr.gopartner.tregusto.reservation.internal;

import fr.gopartner.tregusto.reservation.api.generated.ReservationRequestDTO;
import fr.gopartner.tregusto.reservation.api.generated.ReservationResponseDTO;

public interface ReservationService {


    ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO);
}
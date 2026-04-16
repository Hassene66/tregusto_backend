package fr.gopartner.tregusto.reservation.infrastructure.mapper;

import fr.gopartner.tregusto.common.GlobalMapperConfig;
import fr.gopartner.tregusto.reservation.api.generated.ReservationRequestDTO;
import fr.gopartner.tregusto.reservation.api.generated.ReservationResponseDTO;
import fr.gopartner.tregusto.reservation.domain.Reservation;
import fr.gopartner.tregusto.reservation.domain.ReservationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalTime;
import java.util.UUID;

@Mapper(config = GlobalMapperConfig.class)
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "numeroTable", ignore = true)
    @Mapping(target = "statut", constant = "PASSEE")
    @Mapping(target = "heure", source = "heure", qualifiedByName = "stringToLocalTime")
    Reservation toEntity(ReservationRequestDTO requestDTO);

    @Mapping(target = "uuid", source = "uuid", qualifiedByName = "stringToUUID")
    @Mapping(target = "heure", source = "heure", qualifiedByName = "localTimeToString")
    @Mapping(target = "numeroTable", source = "numeroTable", qualifiedByName = "integerToJsonNullable")
    @Mapping(target = "statut", source = "statut", qualifiedByName = "reservationStatusToEnum")
    ReservationResponseDTO toResponseDTO(Reservation reservation);

    @Named("stringToLocalTime")
    default LocalTime stringToLocalTime(String heure) {
        if (heure == null || heure.isBlank()) {
            return null;
        }
        return LocalTime.parse(heure);
    }

    @Named("localTimeToString")
    default String localTimeToString(LocalTime heure) {
        if (heure == null) {
            return null;
        }
        return heure.toString();
    }

    @Named("stringToUUID")
    default UUID stringToUUID(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            return null;
        }
        return UUID.fromString(uuid);
    }

    @Named("integerToJsonNullable")
    default JsonNullable<Integer> integerToJsonNullable(Integer numeroTable) {
        if (numeroTable == null) {
            return JsonNullable.undefined();
        }
        return JsonNullable.of(numeroTable);
    }

    @Named("reservationStatusToEnum")
    default ReservationResponseDTO.StatutEnum reservationStatusToEnum(ReservationStatus status) {
        if (status == null) {
            return ReservationResponseDTO.StatutEnum.CONFIRMEE;
        }

        switch (status) {
            case CONFIRMEE:
                return ReservationResponseDTO.StatutEnum.CONFIRMEE;
            case ANNULEE:
                return ReservationResponseDTO.StatutEnum.ANNULEE;
            case PASSEE:
                return ReservationResponseDTO.StatutEnum.PASSEE;
            default:
                return ReservationResponseDTO.StatutEnum.CONFIRMEE;
        }
    }
}
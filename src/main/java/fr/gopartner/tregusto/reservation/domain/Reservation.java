package fr.gopartner.tregusto.reservation.domain;



import jakarta.persistence.*;
        import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false, length = 36)
    private String uuid;

    @Column(nullable = false)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;

    @Column(name = "nombre_places", nullable = false)
    @NotNull(message = "Le nombre de places est obligatoire")
    @Min(value = 1, message = "Minimum 1 personne")
    @Max(value = 20, message = "Maximum 20 personnes")
    private Integer nombrePlaces;

    @Column(name = "date_reservation", nullable = false)
    @NotNull(message = "La date est obligatoire")
    @FutureOrPresent(message = "La date doit être aujourd'hui ou dans le futur")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "heure_reservation", nullable = false)
    @NotNull(message = "L'heure est obligatoire")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heure;

    @Column(name = "table_assigned")
    private Integer numeroTable;

    @Enumerated(EnumType.STRING)
    private ReservationStatus statut = ReservationStatus.PASSEE;


    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }


}
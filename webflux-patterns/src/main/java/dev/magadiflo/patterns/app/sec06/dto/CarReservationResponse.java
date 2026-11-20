package dev.magadiflo.patterns.app.sec06.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CarReservationResponse(UUID reservationId,
                                     String city,
                                     LocalDate pickup,
                                     LocalDate drop,
                                     String category,
                                     Integer price) {
}

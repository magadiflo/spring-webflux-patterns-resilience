package dev.magadiflo.patterns.app.sec06.dto;

import java.time.LocalDate;

public record ReservationItemRequest(ReservationType type,
                                     String category,
                                     String city,
                                     LocalDate from,
                                     LocalDate to) {
}

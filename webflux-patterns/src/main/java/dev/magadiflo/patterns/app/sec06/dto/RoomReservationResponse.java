package dev.magadiflo.patterns.app.sec06.dto;

import java.time.LocalDate;
import java.util.UUID;

public record RoomReservationResponse(UUID reservationId,
                                      String city,
                                      LocalDate checkIn,
                                      LocalDate checkOut,
                                      String category,
                                      Integer price) {
}

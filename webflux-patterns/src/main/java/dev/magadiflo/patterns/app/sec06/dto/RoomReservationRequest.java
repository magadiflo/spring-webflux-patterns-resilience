package dev.magadiflo.patterns.app.sec06.dto;

import java.time.LocalDate;

public record RoomReservationRequest(String city,
                                     LocalDate checkIn,
                                     LocalDate checkOut,
                                     String category) {
}

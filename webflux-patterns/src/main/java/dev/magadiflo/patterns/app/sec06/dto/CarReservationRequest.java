package dev.magadiflo.patterns.app.sec06.dto;

import java.time.LocalDate;

public record CarReservationRequest(String city,
                                    LocalDate pickup,
                                    LocalDate drop,
                                    String category) {
}

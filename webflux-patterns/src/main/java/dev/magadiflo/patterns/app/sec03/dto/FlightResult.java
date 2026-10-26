package dev.magadiflo.patterns.app.sec03.dto;

import java.time.LocalDate;

public record FlightResult(String airline,
                           String from,
                           String to,
                           Double price,
                           LocalDate date) {
}

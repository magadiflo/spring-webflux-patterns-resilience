package dev.magadiflo.patterns.app.sec04.dto;

public record PaymentResponse(Integer userId,
                              String name,
                              Integer balance,
                              Status status) {
}

package dev.magadiflo.patterns.app.sec05.dto;

public record PaymentResponse(Integer userId,
                              String name,
                              Integer balance,
                              Status status) {
}

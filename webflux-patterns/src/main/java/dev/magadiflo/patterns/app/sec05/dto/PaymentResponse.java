package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record PaymentResponse(UUID paymentId,
                              Integer userId,
                              String name,
                              Integer balance,
                              Status status) {
}

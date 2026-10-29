package dev.magadiflo.patterns.app.sec04.dto;

import java.util.UUID;

public record PaymentRequest(Integer userId,
                             Integer amount,
                             UUID orderId) {
}

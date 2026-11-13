package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record PaymentRequest(Integer userId,
                             Integer amount,
                             UUID orderId) {
}

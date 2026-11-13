package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record ShippingResponse(UUID orderId,
                               Integer quantity,
                               Status status,
                               String expectedDelivery,
                               Address address) {
}

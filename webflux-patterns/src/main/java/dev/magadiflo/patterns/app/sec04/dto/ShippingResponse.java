package dev.magadiflo.patterns.app.sec04.dto;

import java.util.UUID;

public record ShippingResponse(UUID orderId,
                               Integer quantity,
                               Status status,
                               String expectedDelivery,
                               Address address) {
}

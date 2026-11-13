package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record ShippingResponse(UUID shippingId,
                               Integer quantity,
                               Status status,
                               String expectedDelivery,
                               Address address) {
}

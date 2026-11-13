package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record InventoryRequest(UUID orderId,
                               Integer productId,
                               Integer quantity) {
}

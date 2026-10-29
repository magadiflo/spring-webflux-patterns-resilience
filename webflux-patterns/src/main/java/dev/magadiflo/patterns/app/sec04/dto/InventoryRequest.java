package dev.magadiflo.patterns.app.sec04.dto;

import java.util.UUID;

public record InventoryRequest(UUID orderId,
                               Integer productId,
                               Integer quantity) {
}

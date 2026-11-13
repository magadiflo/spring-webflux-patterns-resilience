package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record InventoryResponse(UUID inventoryId,
                                Integer productId,
                                Integer quantity,
                                Integer remainingQuantity,
                                Status status) {
}

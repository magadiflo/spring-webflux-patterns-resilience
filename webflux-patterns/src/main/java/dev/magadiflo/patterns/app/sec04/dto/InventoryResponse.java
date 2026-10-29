package dev.magadiflo.patterns.app.sec04.dto;

public record InventoryResponse(Integer productId,
                                Integer quantity,
                                Integer remainingQuantity,
                                Status status) {
}

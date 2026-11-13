package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record ShippingRequest(Integer quantity,
                              Integer userId,
                              UUID inventoryId) {
}

package dev.magadiflo.patterns.app.sec04.dto;

import java.util.UUID;

public record ShippingRequest(Integer quantity,
                              Integer userId,
                              UUID orderId) {
}

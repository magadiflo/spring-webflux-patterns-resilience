package dev.magadiflo.patterns.app.sec05.dto;

import java.util.UUID;

public record OrderResponse(Integer userId,
                            Integer productId,
                            UUID orderId,
                            Status status,
                            Address shippingAddress,
                            String expectedDelivery) {
}

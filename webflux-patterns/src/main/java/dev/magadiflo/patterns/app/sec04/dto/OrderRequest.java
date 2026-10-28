package dev.magadiflo.patterns.app.sec04.dto;

public record OrderRequest(Integer userId,
                           Integer productId,
                           Integer quantity) {
}

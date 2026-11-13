package dev.magadiflo.patterns.app.sec05.dto;

public record OrderRequest(Integer userId,
                           Integer productId,
                           Integer quantity) {
}

package dev.magadiflo.patterns.app.sec02.dto;

import java.time.LocalDate;
import java.time.ZoneId;

public record PromotionResponse(Integer id,
                                String type,
                                Double Discount,
                                LocalDate endDate) {

    public static PromotionResponse noPromotion() {
        return new PromotionResponse(-1, "no promotion", 0.0, LocalDate.now(ZoneId.of("America/Lima")));
    }
}

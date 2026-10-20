package dev.magadiflo.patterns.app.sec02.dto;

import java.time.LocalDate;

public record Price(Integer listPrice,
                    Double discount,
                    Double discountedPrice,
                    Double amountSaved,
                    LocalDate endDate) {
}

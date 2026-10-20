package dev.magadiflo.patterns.app.sec02.dto;

import java.util.List;

public record ProductAggregate(Integer id,
                               String category,
                               String description,
                               Price price,
                               List<Review> reviews) {
}

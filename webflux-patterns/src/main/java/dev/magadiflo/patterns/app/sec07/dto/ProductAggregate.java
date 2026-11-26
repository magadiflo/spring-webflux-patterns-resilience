package dev.magadiflo.patterns.app.sec07.dto;

import java.util.List;

public record ProductAggregate(Integer id,
                               String category,
                               String description,
                               Integer price,
                               List<Review> reviews) {
}

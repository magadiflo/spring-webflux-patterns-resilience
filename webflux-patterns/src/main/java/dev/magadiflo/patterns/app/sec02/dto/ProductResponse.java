package dev.magadiflo.patterns.app.sec02.dto;

public record ProductResponse(Integer id,
                              String category,
                              String description,
                              Integer price) {
}

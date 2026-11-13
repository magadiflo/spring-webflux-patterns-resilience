package dev.magadiflo.patterns.app.sec05.dto;

public record ProductResponse(Integer id,
                              String category,
                              String description,
                              Integer price) {
}

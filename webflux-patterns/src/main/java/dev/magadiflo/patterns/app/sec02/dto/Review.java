package dev.magadiflo.patterns.app.sec02.dto;

public record Review(Integer id,
                     String user,
                     Integer rating,
                     String comment) {
}

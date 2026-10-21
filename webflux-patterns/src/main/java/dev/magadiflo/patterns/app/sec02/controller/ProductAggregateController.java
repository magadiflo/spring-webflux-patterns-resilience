package dev.magadiflo.patterns.app.sec02.controller;

import dev.magadiflo.patterns.app.sec02.dto.ProductAggregate;
import dev.magadiflo.patterns.app.sec02.service.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/{version}/sec01/products", version = "1")
public class ProductAggregateController {

    private final ProductAggregatorService service;

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable Integer id) {
        return this.service.aggregate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

package dev.magadiflo.patterns.app.sec04.controller;

import dev.magadiflo.patterns.app.sec04.dto.OrderRequest;
import dev.magadiflo.patterns.app.sec04.dto.OrderResponse;
import dev.magadiflo.patterns.app.sec04.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/{version}/sec03/orders", version = "1")
public class OrderController {

    private final OrchestratorService orchestratorService;

    @PostMapping
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> orderRequestMono) {
        return this.orchestratorService.placeOrder(orderRequestMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

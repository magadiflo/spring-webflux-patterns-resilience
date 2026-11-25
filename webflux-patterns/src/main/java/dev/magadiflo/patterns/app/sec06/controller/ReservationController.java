package dev.magadiflo.patterns.app.sec06.controller;

import dev.magadiflo.patterns.app.sec06.dto.ReservationItemRequest;
import dev.magadiflo.patterns.app.sec06.dto.ReservationResponse;
import dev.magadiflo.patterns.app.sec06.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/{version}/sec05/reservations", version = "1")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(path = "/reserve")
    public Mono<ResponseEntity<ReservationResponse>> reserve(@RequestBody Flux<ReservationItemRequest> requestFlux) {
        return this.reservationService.reserve(requestFlux)
                .map(ResponseEntity::ok);
    }
}

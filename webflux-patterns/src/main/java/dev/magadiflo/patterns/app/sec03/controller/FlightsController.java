package dev.magadiflo.patterns.app.sec03.controller;

import dev.magadiflo.patterns.app.sec03.dto.FlightResult;
import dev.magadiflo.patterns.app.sec03.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/{version}/sec02/flights", version = "1")
public class FlightsController {

    private final FlightSearchService flightSearchService;

    @GetMapping(path = "/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Flux<FlightResult>>> getFlights(@PathVariable String from, @PathVariable String to) {
        return Mono.just(ResponseEntity.ok(this.flightSearchService.getFlight(from, to)));
    }
}

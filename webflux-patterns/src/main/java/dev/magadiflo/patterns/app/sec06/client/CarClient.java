package dev.magadiflo.patterns.app.sec06.client;

import dev.magadiflo.patterns.app.sec06.dto.CarReservationRequest;
import dev.magadiflo.patterns.app.sec06.dto.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CarClient {

    private final WebClient webClient;

    public CarClient(@Value("${sec05.car.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> requestFlux) {
        return this.webClient
                .post()
                .body(requestFlux, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(throwable -> Mono.empty());
    }
}

package dev.magadiflo.patterns.app.sec06.client;

import dev.magadiflo.patterns.app.sec06.dto.RoomReservationRequest;
import dev.magadiflo.patterns.app.sec06.dto.RoomReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RoomClient {
    private final WebClient webClient;

    public RoomClient(@Value("${sec05.room.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> requestFlux) {
        return this.webClient
                .post()
                .body(requestFlux, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(throwable -> Mono.empty());
    }
}

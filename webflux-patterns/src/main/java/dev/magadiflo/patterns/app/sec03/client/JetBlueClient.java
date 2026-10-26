package dev.magadiflo.patterns.app.sec03.client;

import dev.magadiflo.patterns.app.sec03.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JetBlueClient {

    private static final String JETBLUE = "JETBLUE";
    private final WebClient webClient;

    public JetBlueClient(@Value("${sec02.jetblue.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.webClient
                .get()
                .uri("/{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .map(flight -> this.normalizeResponse(flight, from, to))
                .onErrorResume(throwable -> Mono.empty());
    }

    private FlightResult normalizeResponse(FlightResult result, String from, String to) {
        return new FlightResult(JETBLUE, from, to, result.price(), result.date());
    }
}

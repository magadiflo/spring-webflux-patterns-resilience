package dev.magadiflo.patterns.app.sec03.client;

import dev.magadiflo.patterns.app.sec03.dto.FlightResult;
import dev.magadiflo.patterns.app.sec03.dto.FrontierRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class FrontierClient {

    private final WebClient webClient;

    public FrontierClient(@Value("${sec02.frontier.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return this.webClient
                .post()
                .bodyValue(new FrontierRequest(from, to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(throwable -> Flux.empty());
    }
}

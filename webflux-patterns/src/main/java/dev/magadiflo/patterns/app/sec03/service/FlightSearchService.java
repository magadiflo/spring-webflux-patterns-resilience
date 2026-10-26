package dev.magadiflo.patterns.app.sec03.service;

import dev.magadiflo.patterns.app.sec03.client.DeltaClient;
import dev.magadiflo.patterns.app.sec03.client.FrontierClient;
import dev.magadiflo.patterns.app.sec03.client.JetBlueClient;
import dev.magadiflo.patterns.app.sec03.dto.FlightResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class FlightSearchService {

    private final DeltaClient deltaClient;
    private final FrontierClient frontierClient;
    private final JetBlueClient jetBlueClient;

    public Flux<FlightResult> getFlight(String from, String to) {
        return Flux.merge(
                        this.deltaClient.getFlights(from, to),
                        this.frontierClient.getFlights(from, to),
                        this.jetBlueClient.getFlights(from, to)
                )
                .take(Duration.ofSeconds(3));
    }
}

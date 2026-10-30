package dev.magadiflo.patterns.app.sec04.client;

import dev.magadiflo.patterns.app.sec04.dto.ShippingRequest;
import dev.magadiflo.patterns.app.sec04.dto.ShippingResponse;
import dev.magadiflo.patterns.app.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ShippingClient {

    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";
    private final WebClient webClient;

    public ShippingClient(@Value("${sec03.shipping.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest shippingRequest) {
        return this.callShippingService(SCHEDULE, shippingRequest);
    }

    public Mono<ShippingResponse> cancel(ShippingRequest shippingRequest) {
        return this.callShippingService(CANCEL, shippingRequest);
    }

    private Mono<ShippingResponse> callShippingService(String endpoint, ShippingRequest request) {
        return this.webClient
                .post()
                .uri("/{endpoint}", endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private ShippingResponse buildErrorResponse(ShippingRequest request) {
        return new ShippingResponse(
                request.orderId(),
                request.quantity(),
                Status.FAILED,
                null,
                null
        );
    }
}

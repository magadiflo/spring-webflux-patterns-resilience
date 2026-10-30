package dev.magadiflo.patterns.app.sec04.client;

import dev.magadiflo.patterns.app.sec04.dto.InventoryRequest;
import dev.magadiflo.patterns.app.sec04.dto.InventoryResponse;
import dev.magadiflo.patterns.app.sec04.dto.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class InventoryClient {

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";
    private final WebClient webClient;

    public InventoryClient(@Value("${sec03.inventory.service}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest inventoryRequest) {
        return this.callInventoryService(DEDUCT, inventoryRequest);
    }

    public Mono<InventoryResponse> restore(InventoryRequest inventoryRequest) {
        return this.callInventoryService(RESTORE, inventoryRequest);
    }

    private Mono<InventoryResponse> callInventoryService(String endpoint, InventoryRequest request) {
        return this.webClient
                .post()
                .uri("/{endpoint}", endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(this.buildErrorResponse(request));
    }

    private InventoryResponse buildErrorResponse(InventoryRequest request) {
        return new InventoryResponse(
                request.productId(),
                request.quantity(),
                null,
                Status.FAILED
        );
    }
}

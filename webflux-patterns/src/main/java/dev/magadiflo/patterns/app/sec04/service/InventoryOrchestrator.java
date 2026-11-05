package dev.magadiflo.patterns.app.sec04.service;

import dev.magadiflo.patterns.app.sec04.client.InventoryClient;
import dev.magadiflo.patterns.app.sec04.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec04.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class InventoryOrchestrator extends Orchestrator {

    private final InventoryClient inventoryClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.inventoryClient.deduct(context.getInventoryRequest())
                .doOnNext(context::setInventoryResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> context.getInventoryResponse().status() == Status.SUCCESS;
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> {
            Mono.just(context)
                    .filter(isSuccess())
                    .map(OrchestrationRequestContext::getInventoryRequest)
                    .flatMap(this.inventoryClient::restore)
                    .subscribe();
        };
    }
}

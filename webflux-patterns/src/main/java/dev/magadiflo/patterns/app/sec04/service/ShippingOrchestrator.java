package dev.magadiflo.patterns.app.sec04.service;

import dev.magadiflo.patterns.app.sec04.client.ShippingClient;
import dev.magadiflo.patterns.app.sec04.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec04.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class ShippingOrchestrator extends Orchestrator {

    private final ShippingClient shippingClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.shippingClient.schedule(context.getShippingRequest())
                .doOnNext(context::setShippingResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> context.getShippingResponse().status() == Status.SUCCESS;
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> {
            Mono.just(context)
                    .filter(isSuccess())
                    .map(OrchestrationRequestContext::getShippingRequest)
                    .flatMap(this.shippingClient::cancel)
                    .subscribe();
        };
    }
}

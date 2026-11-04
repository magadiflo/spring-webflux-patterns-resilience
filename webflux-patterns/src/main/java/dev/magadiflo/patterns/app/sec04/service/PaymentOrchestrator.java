package dev.magadiflo.patterns.app.sec04.service;

import dev.magadiflo.patterns.app.sec04.client.UserClient;
import dev.magadiflo.patterns.app.sec04.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec04.dto.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentOrchestrator extends Orchestrator {

    private final UserClient userClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context) {
        return this.userClient.deduct(context.getPaymentRequest())
                .doOnNext(context::setPaymentResponse)
                .thenReturn(context);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return context -> context.getPaymentResponse().status() == Status.SUCCESS;
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return context -> {
            Mono.just(context)
                    .filter(isSuccess())
                    .map(OrchestrationRequestContext::getPaymentRequest)
                    .flatMap(this.userClient::refund)
                    .subscribe();
        };
    }
}

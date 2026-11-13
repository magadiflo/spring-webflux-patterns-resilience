package dev.magadiflo.patterns.app.sec05.service;

import dev.magadiflo.patterns.app.sec05.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec05.dto.Status;
import dev.magadiflo.patterns.app.sec05.service.orchestrator.Orchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderFulfillmentService {

    private final List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext context) {
        List<Mono<OrchestrationRequestContext>> listOfMonos = this.orchestrators.stream()
                .map(orchestrator -> orchestrator.create(context))
                .toList();
        return Mono.zip(listOfMonos, objects -> objects[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext context) {
        boolean allSuccess = this.orchestrators.stream()
                .allMatch(orchestrator -> orchestrator.isSuccess().test(context));
        Status status = allSuccess ? Status.SUCCESS : Status.FAILED;
        context.setStatus(status);
    }
}

package dev.magadiflo.patterns.app.sec04.service;

import dev.magadiflo.patterns.app.sec04.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec04.service.orchestrator.Orchestrator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderCancellationService {

    private final List<Orchestrator> orchestrators;

    private Sinks.Many<OrchestrationRequestContext> sink;
    private Flux<OrchestrationRequestContext> flux;

    @PostConstruct
    public void init() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux().publishOn(Schedulers.boundedElastic());

        this.orchestrators.forEach(orchestrator ->
                this.flux.subscribe(orchestrator.cancel())
        );
    }

    public void cancelOrder(OrchestrationRequestContext context) {
        this.sink.emitNext(context, (signalType, emitResult) ->
                emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED
        );
    }
}

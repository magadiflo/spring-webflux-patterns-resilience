package dev.magadiflo.patterns.app.sec05.service.orchestrator;

import dev.magadiflo.patterns.app.sec05.dto.OrchestrationRequestContext;
import dev.magadiflo.patterns.app.sec05.exception.OrderFulfillmentException;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Orchestrator {
    public abstract Mono<OrchestrationRequestContext> create(OrchestrationRequestContext context);

    public abstract Predicate<OrchestrationRequestContext> isSuccess();

    public abstract Consumer<OrchestrationRequestContext> cancel();

    protected BiConsumer<OrchestrationRequestContext, SynchronousSink<OrchestrationRequestContext>> statusHandler() {
        return (context, synchronousSink) -> {
            if (this.isSuccess().test(context)) {
                synchronousSink.next(context);
            } else {
                synchronousSink.error(new OrderFulfillmentException());
            }
        };
    }
}

package dev.magadiflo.patterns.app.sec06.service.handler;

import dev.magadiflo.patterns.app.sec06.dto.ReservationItemRequest;
import dev.magadiflo.patterns.app.sec06.dto.ReservationItemResponse;
import dev.magadiflo.patterns.app.sec06.dto.ReservationType;
import reactor.core.publisher.Flux;

public abstract class ReservationHandler {
    public abstract ReservationType getType();

    public abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> requestFlux);
}

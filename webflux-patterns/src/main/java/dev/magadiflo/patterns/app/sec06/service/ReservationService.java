package dev.magadiflo.patterns.app.sec06.service;

import dev.magadiflo.patterns.app.sec06.dto.ReservationItemRequest;
import dev.magadiflo.patterns.app.sec06.dto.ReservationItemResponse;
import dev.magadiflo.patterns.app.sec06.dto.ReservationResponse;
import dev.magadiflo.patterns.app.sec06.dto.ReservationType;
import dev.magadiflo.patterns.app.sec06.service.handler.ReservationHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final Map<ReservationType, ReservationHandler> handlerMap;

    public ReservationService(List<ReservationHandler> reservationHandlerList) {
        this.handlerMap = reservationHandlerList.stream()
                .collect(Collectors.toMap(
                        ReservationHandler::getType,
                        Function.identity()
                ));
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> requestFlux) {
        return requestFlux.groupBy(ReservationItemRequest::type) // Splitter
                .flatMap(this::aggregator)
                .collectList()
                .map(this::toResponse);
    }

    private Flux<ReservationItemResponse> aggregator(GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux) {
        ReservationType reservationType = groupedFlux.key();
        ReservationHandler reservationHandler = this.handlerMap.get(reservationType);
        return reservationHandler.reserve(groupedFlux);
    }

    private ReservationResponse toResponse(List<ReservationItemResponse> reservationItemResponses) {
        return new ReservationResponse(
                UUID.randomUUID(),
                reservationItemResponses.stream().mapToInt(ReservationItemResponse::price).sum(),
                reservationItemResponses
        );
    }
}

package dev.magadiflo.patterns.app.sec06.service.handler;

import dev.magadiflo.patterns.app.sec06.client.CarClient;
import dev.magadiflo.patterns.app.sec06.dto.CarReservationRequest;
import dev.magadiflo.patterns.app.sec06.dto.CarReservationResponse;
import dev.magadiflo.patterns.app.sec06.dto.ReservationItemRequest;
import dev.magadiflo.patterns.app.sec06.dto.ReservationItemResponse;
import dev.magadiflo.patterns.app.sec06.dto.ReservationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class CarReservationHandler extends ReservationHandler {

    private final CarClient carClient;

    @Override
    public ReservationType getType() {
        return ReservationType.CAR;
    }

    @Override
    public Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> requestFlux) {
        return requestFlux.map(this::toCarRequest)
                .transform(this.carClient::reserve)
                .map(this::toResponse);
    }

    private CarReservationRequest toCarRequest(ReservationItemRequest request) {
        return new CarReservationRequest(
                request.city(),
                request.from(),
                request.to(),
                request.category()
        );
    }

    private ReservationItemResponse toResponse(CarReservationResponse response) {
        return new ReservationItemResponse(
                response.reservationId(),
                this.getType(),
                response.category(),
                response.city(),
                response.pickup(),
                response.drop(),
                response.price()
        );
    }
}

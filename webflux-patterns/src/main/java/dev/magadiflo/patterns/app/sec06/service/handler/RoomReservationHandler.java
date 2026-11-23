package dev.magadiflo.patterns.app.sec06.service.handler;

import dev.magadiflo.patterns.app.sec06.client.RoomClient;
import dev.magadiflo.patterns.app.sec06.dto.ReservationItemRequest;
import dev.magadiflo.patterns.app.sec06.dto.ReservationItemResponse;
import dev.magadiflo.patterns.app.sec06.dto.ReservationType;
import dev.magadiflo.patterns.app.sec06.dto.RoomReservationRequest;
import dev.magadiflo.patterns.app.sec06.dto.RoomReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class RoomReservationHandler extends ReservationHandler {

    private final RoomClient roomClient;

    @Override
    protected ReservationType getType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> requestFlux) {
        return requestFlux.map(this::toRoomRequest)
                .transform(this.roomClient::reserve)
                .map(this::toResponse);
    }

    private RoomReservationRequest toRoomRequest(ReservationItemRequest request) {
        return new RoomReservationRequest(
                request.city(),
                request.from(),
                request.to(),
                request.category()
        );
    }

    private ReservationItemResponse toResponse(RoomReservationResponse response) {
        return new ReservationItemResponse(
                response.reservationId(),
                this.getType(),
                response.category(),
                response.city(),
                response.checkIn(),
                response.checkOut(),
                response.price()
        );
    }
}

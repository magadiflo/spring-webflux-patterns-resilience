package dev.magadiflo.patterns.app.sec06.dto;

import java.util.List;
import java.util.UUID;

public record ReservationResponse(UUID reservationId,
                                  Integer price,
                                  List<ReservationItemResponse> items) {
}

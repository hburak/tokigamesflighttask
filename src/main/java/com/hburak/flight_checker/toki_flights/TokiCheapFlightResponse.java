package com.hburak.flight_checker.toki_flights;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TokiCheapFlightResponse {
    private String route;
    private Long departureTime;
    private Long arrivalTime;
}

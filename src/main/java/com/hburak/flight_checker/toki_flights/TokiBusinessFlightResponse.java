package com.hburak.flight_checker.toki_flights;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TokiBusinessFlightResponse {
    private String departure;
    private String arrival;
    private Long departureTime;
    private Long arrivalTime;
}

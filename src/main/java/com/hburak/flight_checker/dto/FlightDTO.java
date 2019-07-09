package com.hburak.flight_checker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlightDTO {
    private boolean business;
    private String departure;
    private String arrival;
    private Long departureTime;
    private Long arrivalTime;
}

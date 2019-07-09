package com.hburak.flight_checker;

import com.hburak.flight_checker.entity.Flight;
import com.hburak.flight_checker.repository.FlightRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BootstrapTestCase {
    private final FlightRepository flightRepository;

    public Flight registerFlight(Flight flight) {
        flight.setDeparture("Istanbul");
        return flightRepository.save(flight);
    }
}

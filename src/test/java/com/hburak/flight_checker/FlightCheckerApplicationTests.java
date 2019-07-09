package com.hburak.flight_checker;

import com.hburak.flight_checker.entity.Flight;
import com.hburak.flight_checker.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class FlightCheckerApplicationTests {
	@Autowired
	private FlightRepository flightRepository;
	private BootstrapTestCase bootstrapTestCase;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void initUseCase() {
		bootstrapTestCase = new BootstrapTestCase(flightRepository);
	}

	@Test
	void savedFlightHasDeparture() {
		Flight flight = Flight.builder()
				.departure("Istanbul")
				.arrival("Tokyo")
				.departureTime(12313131313L)
				.arrivalTime(1325535353535L)
				.business(true)
				.build();
		Flight savedFlight = bootstrapTestCase.registerFlight(flight);
		assertTrue(flight.getDeparture().equalsIgnoreCase(savedFlight.getDeparture()));
	}
}

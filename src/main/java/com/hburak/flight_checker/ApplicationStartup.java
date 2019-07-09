package com.hburak.flight_checker;

import com.hburak.flight_checker.entity.Flight;
import com.hburak.flight_checker.service.FlightService;
import com.hburak.flight_checker.toki_flights.TokiFlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    private final FlightService flightService;
    private final TokiFlightService tokiFlightService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        log.info("Initializing data for the api...");
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData() throws IOException, JSONException {
        List<Flight> flights = tokiFlightService.getFlights();
        for (Flight f : flights)
            flightService.save(f);
        log.info("Flights fetched from toki games...");
    }
}

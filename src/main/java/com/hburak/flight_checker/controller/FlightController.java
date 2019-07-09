package com.hburak.flight_checker.controller;

import com.hburak.flight_checker.config.ApplicationProperties;
import com.hburak.flight_checker.dto.FlightDTO;
import com.hburak.flight_checker.dto.PageDTO;
import com.hburak.flight_checker.enums.Direction;
import com.hburak.flight_checker.enums.OrderBy;
import com.hburak.flight_checker.exception.PaginationSortingException;
import com.hburak.flight_checker.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final ApplicationProperties applicationProperties;

    @GetMapping("")
    public String hello() {
        return applicationProperties.getHelloMessage();
    }

    @GetMapping("listAll")
    public ResponseEntity<Object> listFlights() {
        Object response = flightService.getFlights();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("filterFlights")
    public PageDTO<FlightDTO> filterFlights(@RequestParam(name = "search", required = false) String search,
                                            @RequestParam(name = "isBusiness", required = false, defaultValue = "false") boolean type,
                                            @RequestParam(name = "orderBy", required = false, defaultValue = "departureTime") String orderBy,
                                            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
                                            @RequestParam(name = "pageIndex", required = false) Integer pageIndex,
                                            @RequestParam(name = "size", required = false) Integer size) {
        if (!(direction.equalsIgnoreCase(Direction.ASCENDING.getDirectionCode())) && !(direction.equalsIgnoreCase(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sorting direction. Fix it and try again.");
        }

        if (!(orderBy.equalsIgnoreCase(OrderBy.DEPARTURE.getOrderBy())) && !(orderBy.equalsIgnoreCase(OrderBy.ARRIVAL.getOrderBy()))) {
            throw new PaginationSortingException("Invalid order by criteria. Fix it and try again.");
        }
        return flightService.filterFlights(search, type, orderBy, direction, pageIndex, size);
    }

    @GetMapping("listByType")
    public ResponseEntity<Object> listFlightsByType(@RequestParam(name = "isBusiness", defaultValue = "false") boolean type) {
        Object response = flightService.getFlightsByType(type);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

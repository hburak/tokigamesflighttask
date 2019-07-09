package com.hburak.flight_checker.service;

import com.hburak.flight_checker.dto.FlightDTO;
import com.hburak.flight_checker.dto.PageDTO;
import com.hburak.flight_checker.entity.Flight;

import java.util.List;

public interface FlightService {
    void save(Flight flight);
    void update(Flight flight);
    void delete(Long id);
    Flight getOne(Long id);
    List<Flight> getFlights();
    List<Flight> getFlightsByType(boolean type);
    PageDTO<FlightDTO> filterFlights(String search, boolean type, String orderBy, String direction, Integer pageIndex, Integer size);
}

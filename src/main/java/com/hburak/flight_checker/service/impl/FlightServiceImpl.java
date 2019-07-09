package com.hburak.flight_checker.service.impl;

import com.hburak.flight_checker.config.ApplicationProperties;
import com.hburak.flight_checker.dto.FlightDTO;
import com.hburak.flight_checker.dto.PageDTO;
import com.hburak.flight_checker.entity.Flight;
import com.hburak.flight_checker.repository.FlightRepository;
import com.hburak.flight_checker.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    @Override
    public void update(Flight flight) {
        flightRepository.save(flight);
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight getOne(Long id) {
        return flightRepository.getOne(id);
    }

    @Override
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getFlightsByType(boolean type) {
        return flightRepository.findByBusiness(type);
    }

    @Override
    public PageDTO<FlightDTO> filterFlights(String search, boolean type, String orderBy, String direction, Integer pageIndex, Integer size) {
        if(search != null) { search = search.trim(); }
        if(size == null || size == 0) { size = applicationProperties.getDefaultPageSize(); }
        if(pageIndex == null) { pageIndex = 0; }

        Page<Flight> flights = null;
        if (direction.equalsIgnoreCase("ASC")) {
            flights = flightRepository.findFlightsByFilter(search, type, PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.ASC, orderBy)));
        } else {
            flights = flightRepository.findFlightsByFilter(search, type, PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.DESC, orderBy)));
        }

        PageDTO<FlightDTO> flightDTO = new PageDTO<>();
        flightDTO.setTotalElements(flights.getTotalElements());
        flightDTO.setTotalPages(flights.getTotalPages());
        flightDTO.setContent(flights.getContent().stream().map(flight -> {
            FlightDTO fDto = new FlightDTO();
            fDto.setArrival(flight.getArrival());
            fDto.setArrivalTime(flight.getArrivalTime());
            fDto.setBusiness(flight.isBusiness());
            fDto.setDeparture(flight.getDeparture());
            fDto.setDepartureTime(flight.getDepartureTime());
            return fDto;
        }).collect(Collectors.toList()));
        return flightDTO;
    }
}

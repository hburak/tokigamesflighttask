package com.hburak.flight_checker.repository;

import com.hburak.flight_checker.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(
            "SELECT DISTINCT f FROM Flight f WHERE (:search IS NULL OR :search = '' OR lower(f.departure) like lower(concat('%', :search,'%')) " +
                    "OR lower(f.arrival) like lower(concat('%', :search,'%')) OR lower(f.departureTime) like lower(concat('%', :search,'%')) " +
                    "OR lower(f.arrivalTime) like lower(concat('%', :search,'%'))) AND f.business = :type"
            )
    Page<Flight> findFlightsByFilter(@Param("search") String search, @Param("type") boolean type, Pageable pageable);

    List<Flight> findByBusiness(boolean type);
    List<Flight> findByDeparture(String departure);
    List<Flight> findByArrival(String arrival);
    List<Flight> findByDepartureAndArrival(String departure, String arrival);
}

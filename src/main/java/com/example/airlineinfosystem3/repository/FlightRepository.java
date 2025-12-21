package com.example.airlineinfosystem3.repository;

import com.example.airlineinfosystem3.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Поиск по городу вылета и прилёта (частичное совпадение, без учёта регистра)
    List<Flight> findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(
            String origin,
            String destination
    );
}




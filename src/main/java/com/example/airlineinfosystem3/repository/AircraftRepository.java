package com.example.airlineinfosystem3.repository;

import com.example.airlineinfosystem3.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}

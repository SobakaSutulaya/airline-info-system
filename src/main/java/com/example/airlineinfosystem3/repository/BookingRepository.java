package com.example.airlineinfosystem3.repository;

import com.example.airlineinfosystem3.model.Booking;
import com.example.airlineinfosystem3.model.Flight;
import com.example.airlineinfosystem3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    boolean existsByUserAndFlight(User user, Flight flight);

    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.flight.id = :flightId")
    void deleteByFlightId(@Param("flightId") Long flightId);
}

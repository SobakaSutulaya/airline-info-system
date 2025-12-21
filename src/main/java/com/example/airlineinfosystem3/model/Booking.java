package com.example.airlineinfosystem3.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "flight_id"})
        }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;

    @Column(name = "passport_number", length = 50)
    private String passportNumber;

    @Column(name = "address", length = 255)
    private String address;

    public Booking() {
    }

    public Booking(User user, Flight flight, LocalDateTime bookedAt) {
        this.user = user;
        this.flight = flight;
        this.bookedAt = bookedAt;
    }

    public Booking(User user, Flight flight, LocalDateTime bookedAt, String passportNumber, String address) {
        this.user = user;
        this.flight = flight;
        this.bookedAt = bookedAt;
        this.passportNumber = passportNumber;
        this.address = address;
    }

    // -------- getters / setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

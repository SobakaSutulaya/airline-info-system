package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Booking;
import com.example.airlineinfosystem3.model.Flight;
import com.example.airlineinfosystem3.model.User;
import com.example.airlineinfosystem3.repository.BookingRepository;
import com.example.airlineinfosystem3.repository.FlightRepository;
import com.example.airlineinfosystem3.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class BookingController {

    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public BookingController(FlightRepository flightRepository,
                             UserRepository userRepository,
                             BookingRepository bookingRepository) {
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/booking/confirm")
    public String confirmPage(@RequestParam("flightId") Long flightId,
                              @AuthenticationPrincipal UserDetails principal,
                              HttpServletRequest request,
                              Model model) {

        if (principal == null) return "redirect:/login";

        // админов не пускаем
        if (request.isUserInRole("ADMIN")) return "redirect:/";

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (bookingRepository.existsByUserAndFlight(user, flight)) {
            return "redirect:/profile?alreadyBooked";
        }

        // Проверка доступности мест
        if (flight.getAvailableSeats() == null || flight.getAvailableSeats() <= 0) {
            return "redirect:/tickets?noSeatsAvailable";
        }

        model.addAttribute("title", "Confirm booking");
        model.addAttribute("flight", flight);
        model.addAttribute("user", user);

        return "booking-confirm";
    }

    @PostMapping("/booking/confirm")
    @Transactional
    public String confirmBooking(@RequestParam("flightId") Long flightId,
                                 @RequestParam("passportNumber") String passportNumber,
                                 @RequestParam("address") String address,
                                 @AuthenticationPrincipal UserDetails principal,
                                 HttpServletRequest request) {

        if (principal == null) return "redirect:/login";
        if (request.isUserInRole("ADMIN")) return "redirect:/";

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (bookingRepository.existsByUserAndFlight(user, flight)) {
            return "redirect:/profile?alreadyBooked";
        }

        // Проверка доступности мест
        if (flight.getAvailableSeats() == null || flight.getAvailableSeats() <= 0) {
            return "redirect:/tickets?noSeatsAvailable";
        }

        // Создание бронирования и уменьшение количества доступных мест
        Booking booking = new Booking(user, flight, LocalDateTime.now(), passportNumber, address);
        bookingRepository.save(booking);

        // Уменьшаем количество доступных мест
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        return "redirect:/profile?bookingSuccess";
    }
}

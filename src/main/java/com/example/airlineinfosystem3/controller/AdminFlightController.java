package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Flight;
import com.example.airlineinfosystem3.repository.BookingRepository;
import com.example.airlineinfosystem3.repository.FlightRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/flights")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFlightController {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    public AdminFlightController(FlightRepository flightRepository, BookingRepository bookingRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    // ---- форма добавления нового рейса ----
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("title", "Add Flight");
        model.addAttribute("flight", new Flight());
        return "flight-form";
    }

    // ---- форма редактирования существующего рейса ----
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + id));
        model.addAttribute("title", "Edit Flight");
        model.addAttribute("flight", flight);
        return "flight-form";
    }

    // ---- сохранение ----
    @PostMapping("/save")
    public String save(@ModelAttribute("flight") Flight flight) {

        if (flight.getId() == null && flight.getAvailableSeats() == null) {
            flight.setAvailableSeats(150);
        }

        else if (flight.getId() != null && flight.getAvailableSeats() == null) {
            flight.setAvailableSeats(150);
        }
        
        flightRepository.save(flight);
        return "redirect:/schedule";
    }

    // ---- удаление ----
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // Сначала удаляем все связанные бронирования
        bookingRepository.deleteByFlightId(id);
        // Затем удаляем сам рейс
        flightRepository.deleteById(id);
        return "redirect:/schedule";
    }
}

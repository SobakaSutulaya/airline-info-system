package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.repository.FlightRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    private final FlightRepository flightRepository;

    public ScheduleController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
        model.addAttribute("title", "Flight Schedule");
        model.addAttribute("flights", flightRepository.findAll());
        return "schedule"; // resources/templates/schedule.html
    }
}

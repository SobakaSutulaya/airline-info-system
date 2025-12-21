package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Aircraft;
import com.example.airlineinfosystem3.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping("/aircraft")
    public String list(Model model) {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        model.addAttribute("title", "Aircraft Fleet");
        model.addAttribute("aircraftList", aircraftList);
        return "aircraft-list";
    }

    @GetMapping("/aircraft/{id}")
    public String details(@PathVariable Long id, Model model) {
        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        if (aircraft == null) {
            return "redirect:/aircraft";
        }
        model.addAttribute("title", aircraft.getName());
        model.addAttribute("aircraft", aircraft);
        return "aircraft-detail";
    }
}

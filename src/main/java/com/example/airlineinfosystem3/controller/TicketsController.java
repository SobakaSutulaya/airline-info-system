package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Flight;
import com.example.airlineinfosystem3.repository.FlightRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
public class TicketsController {

    private final FlightRepository flightRepository;

    public TicketsController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/tickets")
    public String tickets(@RequestParam(name = "from", required = false) String from,
                          @RequestParam(name = "to", required = false) String to,
                          @RequestParam(name = "sort", required = false, defaultValue = "departure") String sort,
                          Model model) {

        List<Flight> flights;

        if ((from == null || from.isBlank()) && (to == null || to.isBlank())) {
            // без фильтров — показываем всё расписание
            flights = flightRepository.findAll();
        } else {
            String fromFilter = from == null ? "" : from;
            String toFilter = to == null ? "" : to;

            flights = flightRepository
                    .findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(
                            fromFilter, toFilter
                    );
        }

        // Применяем сортировку
        flights = sortFlights(flights, sort);

        model.addAttribute("title", "Бронирование билетов");
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("sort", sort);
        model.addAttribute("flights", flights);

        return "tickets";
    }

    private List<Flight> sortFlights(List<Flight> flights, String sortBy) {
        return flights.stream()
                .sorted(getComparator(sortBy))
                .toList();
    }

    private Comparator<Flight> getComparator(String sortBy) {
        return switch (sortBy) {
            case "departure" -> Comparator.comparing(Flight::getDepartureTime);
            case "departure_desc" -> Comparator.comparing(Flight::getDepartureTime).reversed();
            case "arrival" -> Comparator.comparing(Flight::getArrivalTime);
            case "arrival_desc" -> Comparator.comparing(Flight::getArrivalTime).reversed();
            case "flightNumber" -> Comparator.comparing(Flight::getFlightNumber);
            case "flightNumber_desc" -> Comparator.comparing(Flight::getFlightNumber).reversed();
            case "airline" -> Comparator.comparing(Flight::getAirline);
            case "airline_desc" -> Comparator.comparing(Flight::getAirline).reversed();
            case "status" -> Comparator.comparing(Flight::getStatus);
            case "status_desc" -> Comparator.comparing(Flight::getStatus).reversed();
            default -> Comparator.comparing(Flight::getDepartureTime);
        };
    }
}

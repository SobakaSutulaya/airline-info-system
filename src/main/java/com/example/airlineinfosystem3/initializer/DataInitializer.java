package com.example.airlineinfosystem3.initializer;

import com.example.airlineinfosystem3.model.Flight;
import com.example.airlineinfosystem3.model.PageContent;
import com.example.airlineinfosystem3.repository.FlightRepository;
import com.example.airlineinfosystem3.repository.PageContentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PageContentRepository pageContentRepository;
    private final FlightRepository flightRepository;

    public DataInitializer(PageContentRepository pageContentRepository,
                          FlightRepository flightRepository) {
        this.pageContentRepository = pageContentRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public void run(String... args) {

        if (pageContentRepository.findById("HOME").isEmpty()) {
            pageContentRepository.save(new PageContent(
                    "HOME",
                    "Airline Information System",
                    "Добро пожаловать!",
                    "Добро пожаловать в информационную систему авиакомпании."
            ));
        }

        if (pageContentRepository.findById("ABOUT").isEmpty()) {
            pageContentRepository.save(new PageContent(
                    "ABOUT",
                    "About",
                    "О системе",
                    "Текст страницы «О нас»."
            ));
        }

        if (pageContentRepository.findById("RADAR").isEmpty()) {
            pageContentRepository.save(new PageContent(
                    "RADAR",
                    "Radar",
                    "Онлайн-радар рейсов",
                    "Здесь будет интерактивная карта/радар и информация о рейсах."
            ));
        }

        // Обновляем все существующие рейсы: устанавливаем availableSeats = 150, если оно null или 0
        List<Flight> flights = flightRepository.findAll();
        boolean updated = false;
        for (Flight flight : flights) {
            if (flight.getAvailableSeats() == null || flight.getAvailableSeats() == 0) {
                flight.setAvailableSeats(150);
                flightRepository.save(flight);
                updated = true;
            }
        }
        if (updated) {
            System.out.println("Обновлено количество доступных мест для существующих рейсов до 150.");
        }
    }
}

package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Booking;
import com.example.airlineinfosystem3.model.Flight;
import com.example.airlineinfosystem3.model.User;
import com.example.airlineinfosystem3.repository.BookingRepository;
import com.example.airlineinfosystem3.repository.FlightRepository;
import com.example.airlineinfosystem3.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public ProfileController(UserRepository userRepository,
                             BookingRepository bookingRepository,
                             FlightRepository flightRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    // ---------- просмотр профиля ----------
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails principal,
                          Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // все бронирования этого пользователя
        List<Booking> bookings = bookingRepository.findByUser(user);

        model.addAttribute("title", "Мой профиль");
        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);

        return "profile";
    }

    // ---------- форма редактирования ----------
    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal UserDetails principal,
                              Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        model.addAttribute("title", "Редактирование профиля");
        model.addAttribute("user", user);

        return "profile-edit";
    }

    // ---------- сохранение профиля ----------
    @PostMapping("/profile/edit")
    public String updateProfile(@AuthenticationPrincipal UserDetails principal,
                                @ModelAttribute("user") User formUser,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // разрешённые поля для изменения
        user.setFullName(formUser.getFullName());
        user.setEmail(formUser.getEmail());
        user.setPhone(formUser.getPhone());

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("profileUpdated", true);
        return "redirect:/profile";
    }

    // ---------- отмена бронирования ----------
    @PostMapping("/profile/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable("id") Long bookingId,
                                @AuthenticationPrincipal UserDetails principal,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        bookingRepository.findById(bookingId).ifPresent(booking -> {
            // безопасность: отменяем только свою бронь
            if (booking.getUser() != null &&
                    booking.getUser().getId().equals(user.getId())) {

                // Увеличиваем количество доступных мест при отмене бронирования
                Flight flight = booking.getFlight();
                if (flight != null && flight.getAvailableSeats() != null) {
                    flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                    flightRepository.save(flight);
                }

                // можно либо удалить, либо пометить статусом "CANCELLED"
                // Сейчас просто удалим запись:
                bookingRepository.delete(booking);
            }
        });

        redirectAttributes.addFlashAttribute("bookingCancelled", true);
        return "redirect:/profile";
    }
}

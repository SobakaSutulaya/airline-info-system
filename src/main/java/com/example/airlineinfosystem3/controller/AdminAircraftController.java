package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Aircraft;
import com.example.airlineinfosystem3.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/admin/aircraft")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    private final Path uploadDir = Paths.get("uploads/aircraft");

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        Files.createDirectories(uploadDir);

        String originalName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String extension = "";

        int dot = originalName.lastIndexOf('.');
        if (dot >= 0) {
            extension = originalName.substring(dot);
        }

        String fileName = UUID.randomUUID() + extension;

        try (InputStream is = imageFile.getInputStream()) {
            Files.copy(is, uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        // URL, который будем хранить в БД
        return "/uploads/aircraft/" + fileName;
    }

    // ----- Добавление -----
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("title", "Add Aircraft");
        model.addAttribute("aircraft", new Aircraft());
        return "aircraft-form";
    }

    @PostMapping("/add")
    public String addAircraft(@ModelAttribute("aircraft") Aircraft aircraft,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        String imageUrl = saveImage(imageFile);
        if (imageUrl != null) {
            aircraft.setImageUrl(imageUrl);
        }

        aircraftRepository.save(aircraft);
        return "redirect:/aircraft";
    }

    // ----- Редактирование -----
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        if (aircraft == null) {
            return "redirect:/aircraft";
        }
        model.addAttribute("title", "Edit Aircraft");
        model.addAttribute("aircraft", aircraft);
        return "aircraft-form";
    }

    @PostMapping("/edit/{id}")
    public String updateAircraft(@PathVariable Long id,
                                 @ModelAttribute("aircraft") Aircraft formAircraft,
                                 @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        if (aircraft == null) {
            return "redirect:/aircraft";
        }

        aircraft.setName(formAircraft.getName());
        aircraft.setShortInfo(formAircraft.getShortInfo());
        aircraft.setDetails(formAircraft.getDetails());

        String imageUrl = saveImage(imageFile);
        if (imageUrl != null) {
            aircraft.setImageUrl(imageUrl);
        }

        aircraftRepository.save(aircraft);
        return "redirect:/aircraft";
    }

    // ----- Удаление -----
    @PostMapping("/delete/{id}")
    public String deleteAircraft(@PathVariable Long id) {
        aircraftRepository.deleteById(id);
        return "redirect:/aircraft";
    }
}

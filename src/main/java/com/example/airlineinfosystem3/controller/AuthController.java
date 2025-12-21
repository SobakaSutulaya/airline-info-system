package com.example.airlineinfosystem3.controller;

import com.example.airlineinfosystem3.model.Role;
import com.example.airlineinfosystem3.model.User;
import com.example.airlineinfosystem3.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- LOGIN ----------

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "registered", required = false) String registered,
            Model model
    ) {
        model.addAttribute("title", "Login");

        if (registered != null) {
            model.addAttribute("message", "Регистрация прошла успешно. Войдите под своим логином и паролем.");
        }

        return "login";
    }

    // ---------- REGISTER FORM (GET) ----------

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("user", new User()); // объект для формы
        return "register";
    }

    // ---------- REGISTER SUBMIT (POST) ----------

    @PostMapping("/register")
    public String registerSubmit(
            @ModelAttribute("user") User user,
            Model model
    ) {
        // Проверка: логин уже существует?
        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            model.addAttribute("title", "Register");
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            // вернуть введённые данные
            model.addAttribute("user", user);
            return "register";
        }

        // Шифруем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Всем новым пользователям роль USER
        user.setRole(Role.USER);

        // fullName, email и т.п. уже приехали из формы
        userRepository.save(user);

        // редирект на /login с флагом ?registered
        return "redirect:/login?registered";
    }
}

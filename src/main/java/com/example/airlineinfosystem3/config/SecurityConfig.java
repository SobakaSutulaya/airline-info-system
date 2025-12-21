package com.example.airlineinfosystem3.config;

import com.example.airlineinfosystem3.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ---------- PasswordEncoder ----------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ---------- UserDetailsService ----------
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build()
                )
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // ---------- AuthenticationProvider ----------
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // ---------- SecurityFilterChain ----------
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authProvider
    ) throws Exception {

        http.authenticationProvider(authProvider);

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ---------- публичные страницы ----------
                        .requestMatchers(
                                "/", "/about",
                                "/schedule", "/tickets", "/radar",
                                "/aircraft/**",
                                "/login", "/register",
                                "/css/**", "/js/**", "/images/**", "/uploads/**"
                        ).permitAll()

                        // ---------- редактирование главной и about ----------
                        .requestMatchers(
                                "/admin/home/**",
                                "/admin/about/**"
                        ).hasAnyRole("ADMIN", "MANAGER")

                        // ---------- бронирование ----------
                        .requestMatchers("/booking/**").hasRole("USER")
                        .requestMatchers("/profile/**").hasRole("USER")

                        // ---------- полный админ ----------
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}

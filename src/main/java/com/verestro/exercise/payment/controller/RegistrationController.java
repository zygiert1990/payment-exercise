package com.verestro.exercise.payment.controller;

import com.verestro.exercise.payment.model.NotificationChannel;
import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.persistence.model.UserId;
import com.verestro.exercise.payment.persistence.repository.UserRepository;
import com.verestro.exercise.payment.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    // just for testing purposes
    private final UserRepository userRepository;

    @PostMapping(value = "register")
    public ResponseEntity<UserId> register(@RequestBody @Valid UserDTO user) {
        return ResponseEntity.ok(registrationService.register(user));
    }

    @GetMapping(value = "user/{username}")
    public ResponseEntity<UserDTO> get(@PathVariable("username") String username) {
        return userRepository.findByUsername(username)
                .map(u -> ResponseEntity.ok(UserDTO.builder()
                        .username(u.getUsername())
                        .password(u.getPassword())
                        .phoneNumber(u.getPhoneNumber())
                        .email(u.getEmail())
                        .preferredNotificationChannel(NotificationChannel.valueOf(u.getPreferredNotificationChannel().name()))
                        .build()))
                .orElse(null);
    }

}

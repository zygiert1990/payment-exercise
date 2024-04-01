package com.verestro.exercise.payment.controller;

import com.verestro.exercise.payment.model.UserId;
import com.verestro.exercise.payment.model.UserRegistrationDTO;
import com.verestro.exercise.payment.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping(value = "register")
    public ResponseEntity<UserId> register(@RequestBody @Valid UserRegistrationDTO user) {
        return ResponseEntity.ok(userRegistrationService.register(user));
    }

}

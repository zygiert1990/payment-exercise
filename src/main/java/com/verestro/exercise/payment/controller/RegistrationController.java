package com.verestro.exercise.payment.controller;

import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.model.UserRegistrationDTO;
import com.verestro.exercise.payment.persistence.model.UserId;
import com.verestro.exercise.payment.service.UserRegistrationService;
import com.verestro.exercise.payment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping(value = "register")
    public ResponseEntity<UserId> register(@RequestBody @Valid UserRegistrationDTO user) {
        return ResponseEntity.ok(userRegistrationService.register(user));
    }

    @GetMapping(value = "user/{username}")
    public ResponseEntity<UserDTO> get(@PathVariable("username") String username) {
        return ResponseEntity.badRequest().header("error", "cutom").build();
//        return ResponseEntity.ok(userService.getLoggedUser());
    }

}

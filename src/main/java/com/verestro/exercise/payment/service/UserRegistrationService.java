package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.model.UserRegistrationDTO;
import com.verestro.exercise.payment.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserId register(UserRegistrationDTO user) {
        return new UserId(userService.save(mapToUser(user)).id());
    }

    private UserDTO mapToUser(UserRegistrationDTO user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .preferredNotificationChannel(user.getPreferredNotificationChannel())
                .build();
    }

}

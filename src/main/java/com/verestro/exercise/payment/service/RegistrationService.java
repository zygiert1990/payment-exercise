package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.persistence.model.NotificationChannel;
import com.verestro.exercise.payment.persistence.model.UserEntity;
import com.verestro.exercise.payment.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(UserDTO user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .preferredNotificationChannel(NotificationChannel.valueOf(user.getPreferredNotificationChannel().name()))
                .build();
        userRepository.save(entity);
        return userRepository.findByUsername(user.getUsername())
                .map(UserEntity::getId)
                .orElseThrow(() -> new IllegalStateException("Can not find suer with username: " + user.getUsername()));
    }

}

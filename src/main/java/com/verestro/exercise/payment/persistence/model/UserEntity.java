package com.verestro.exercise.payment.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "USERS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 9, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Enumerated(STRING)
    @Column(nullable = false)
    private NotificationChannel preferredNotificationChannel;

}

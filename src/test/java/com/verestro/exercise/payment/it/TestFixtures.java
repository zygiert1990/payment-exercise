package com.verestro.exercise.payment.it;

import com.verestro.exercise.payment.model.UserRegistrationDTO;
import lombok.experimental.UtilityClass;

import static com.verestro.exercise.payment.model.NotificationChannel.SMS;

@UtilityClass
class TestFixtures {

    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String USERNAME_2 = "username2";

    static UserRegistrationDTO validUser() {
        return UserRegistrationDTO.builder()
                .username(USERNAME)
                .email("email@email")
                .password(PASSWORD)
                .phoneNumber("123456789")
                .preferredNotificationChannel(SMS)
                .build();
    }

    static UserRegistrationDTO anotherUser() {
        return UserRegistrationDTO.builder()
                .username(USERNAME_2)
                .email("email2@email")
                .password("password")
                .phoneNumber("987654321")
                .preferredNotificationChannel(SMS)
                .build();
    }

}

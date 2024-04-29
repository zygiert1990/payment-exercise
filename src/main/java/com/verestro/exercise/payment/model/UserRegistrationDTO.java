package com.verestro.exercise.payment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegistrationDTO {

    @NotEmpty
    @Size(max = 100)
    private String username;

    @NotEmpty
    private String password;

    @Pattern(regexp = "^\\d{9}$", message = "Phone number can only have 9 digits!")
    private String phoneNumber;

    @Email
    private String email;

    private NotificationChannel preferredNotificationChannel;

}

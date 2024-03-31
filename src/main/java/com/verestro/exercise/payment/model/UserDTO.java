package com.verestro.exercise.payment.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserDTO(String id, String username, String password, String phoneNumber, String email,
                      NotificationChannel preferredNotificationChannel, AccountDTO account) {
}

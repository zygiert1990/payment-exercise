package com.verestro.exercise.payment.model;

public record UserCommunicationDTO(NotificationChannel notificationChannel, String phoneNumber, String email) {
}

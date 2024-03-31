package com.verestro.exercise.payment.model;

import lombok.Builder;

@Builder
public record AccountDTO(String id, String accountNumber, int sum) {
}

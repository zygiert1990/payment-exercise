package com.verestro.exercise.payment.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record AccountDTO(String id, String accountNumber, int funds) {
}

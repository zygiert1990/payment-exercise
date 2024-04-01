package com.verestro.exercise.payment.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record TransferCountDTO(String id, LocalDate date, int count, AccountDTO account) {
}

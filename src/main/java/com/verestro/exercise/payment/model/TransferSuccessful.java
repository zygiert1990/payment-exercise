package com.verestro.exercise.payment.model;

public record TransferSuccessful() implements TransferResult {
    @Override
    public String getMessage() {
        return "Transfer successful";
    }
}

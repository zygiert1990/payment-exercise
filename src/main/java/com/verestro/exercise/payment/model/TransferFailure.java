package com.verestro.exercise.payment.model;

public record TransferFailure(String message) implements TransferResult {
    @Override
    public String getMessage() {
        return message;
    }
}

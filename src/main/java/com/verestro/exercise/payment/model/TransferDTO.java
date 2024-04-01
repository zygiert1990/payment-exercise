package com.verestro.exercise.payment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TransferDTO {

    @Size(min = 20, max = 20)
    private String sourceAccount;

    @Size(min = 20, max = 20)
    private String targetAccount;

    @Min(1)
    private int amount;

}

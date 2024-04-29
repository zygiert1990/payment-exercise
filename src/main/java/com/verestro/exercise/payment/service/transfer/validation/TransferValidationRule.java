package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferResult;
import io.vavr.control.Either;

@FunctionalInterface
public interface TransferValidationRule {
    Either<TransferResult, TransferDTO> validate(TransferDTO transfer);
}

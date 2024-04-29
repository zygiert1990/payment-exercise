package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferFailure;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.service.AccountService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TargetAccountExists implements TransferValidationRule {

    private final AccountService accountService;

    @Override
    public Either<TransferResult, TransferDTO> validate(TransferDTO transfer) {
        if (accountService.existsByAccountNumber(transfer.getTargetAccount())) {
            return Either.right(transfer);
        } else {
            return Either.left(new TransferFailure("Currently logged user is not owner of the source account!"));
        }
    }
}

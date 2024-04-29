package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferFailure;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.security.UsernameProvider;
import com.verestro.exercise.payment.service.AccountService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SourceAccountOwedByLoggedUser implements TransferValidationRule {

    private final AccountService accountService;
    private final UsernameProvider usernameProvider;

    @Override
    public Either<TransferResult, TransferDTO> validate(TransferDTO transfer) {
        if (accountService.existsByAccountNumberAndUsername(transfer.getSourceAccount(), usernameProvider.provideUsername())) {
            return Either.right(transfer);
        } else {
            return Either.left(new TransferFailure("Currently logged user is not owner of the source account!"));
        }
    }
}

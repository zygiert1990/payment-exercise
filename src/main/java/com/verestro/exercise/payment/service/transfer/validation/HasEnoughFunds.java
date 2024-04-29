package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferFailure;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.persistence.repository.AccountFunds;
import com.verestro.exercise.payment.service.AccountService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class HasEnoughFunds implements TransferValidationRule {

    private final AccountService accountService;

    @Override
    public Either<TransferResult, TransferDTO> validate(TransferDTO transfer) {
        return accountService.findFundsByAccountNumber(transfer.getSourceAccount())
                .map(accountFunds -> checkIfEnoughFunds(accountFunds, transfer))
                .orElseGet(() -> Either.right(transfer));
    }

    private Either<TransferResult, TransferDTO> checkIfEnoughFunds(AccountFunds accountFunds, TransferDTO transfer) {
        return accountFunds.getFunds() - transfer.getAmount() < 0 ?
                Either.left(new TransferFailure("Not enough funds for perform a transfer!")) :
                Either.right(transfer);
    }

}

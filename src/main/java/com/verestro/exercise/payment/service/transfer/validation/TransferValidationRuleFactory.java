package com.verestro.exercise.payment.service.transfer.validation;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TransferValidationRuleFactory {

    private final SourceAccountOwedByLoggedUser sourceAccountOwedByLoggedUser;
    private final TargetAccountExists targetAccountExists;
    private final AllowedNumberOfTransfersExceeded allowedNumberOfTransfersExceeded;
    private final HasEnoughFunds hasEnoughFunds;

    List<TransferValidationRule> rules() {
        return List.of(
                sourceAccountOwedByLoggedUser,
                targetAccountExists,
                allowedNumberOfTransfersExceeded,
                hasEnoughFunds
        );
    }

}

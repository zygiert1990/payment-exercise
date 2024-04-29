package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferResult;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferValidator {

    private final TransferValidationRuleFactory transferValidationRuleFactory;

    public Either<TransferResult, TransferDTO> validate(TransferDTO transfer) {
        return validate(transfer, transferValidationRuleFactory.rules());
    }

    private Either<TransferResult, TransferDTO> validate(TransferDTO transfer, List<TransferValidationRule> rules) {
        if (rules.isEmpty()) {
            return Either.right(transfer);
        } else {
            Either<TransferResult, TransferDTO> validated = rules.head().validate(transfer);
            if (validated.isLeft()) {
                return validated;
            } else {
                return validate(transfer, rules.tail());
            }
        }
    }

}

package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferFailure;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.persistence.repository.TransferCount;
import com.verestro.exercise.payment.service.TransferCountService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
class AllowedNumberOfTransfersExceeded implements TransferValidationRule {

    private final TransferCountService transferCountService;

    @Override
    public Either<TransferResult, TransferDTO> validate(TransferDTO transfer) {
        return transferCountService.findTransferCountByAccountNumberAndDate(transfer.getSourceAccount(), LocalDate.now())
                .map(transferCount -> checkIfTransferCountExceeded(transfer, transferCount))
                .orElseGet(() -> Either.right(transfer));
    }

    private Either<TransferResult, TransferDTO> checkIfTransferCountExceeded(TransferDTO transfer, TransferCount transferCount) {
        return transferCount.getCount() < 3 ?
                Either.right(transfer) :
                Either.left(new TransferFailure("Exceeded maximum number of transfers for account number: " + transfer.getSourceAccount()));
    }

}

package com.verestro.exercise.payment.service.transfer;

import com.verestro.exercise.payment.model.TransferAccountIds;
import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.model.TransferSuccessful;
import com.verestro.exercise.payment.service.notification.NotificationService;
import com.verestro.exercise.payment.service.transfer.processing.TransferProcessor;
import com.verestro.exercise.payment.service.transfer.validation.TransferValidator;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferValidator transferValidator;
    private final TransferProcessor transferProcessor;
    private final NotificationService notificationService;

    public TransferResult transferFunds(TransferDTO transfer) {
        Either<TransferResult, TransferDTO> validated = transferValidator.validate(transfer);
        if (validated.isLeft()) {
            return validated.getLeft();
        } else {
            TransferAccountIds transferAccountIds = transferProcessor.process(transfer);
            notificationService.notify(transferAccountIds);
            return new TransferSuccessful();
        }
    }

}

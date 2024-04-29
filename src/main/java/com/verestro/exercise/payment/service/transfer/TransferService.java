package com.verestro.exercise.payment.service.transfer;

import com.verestro.exercise.payment.model.TransferAccountIds;
import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.model.TransferSuccessful;
import com.verestro.exercise.payment.service.notification.NotificationService;
import com.verestro.exercise.payment.service.transfer.processing.TransferProcessor;
import com.verestro.exercise.payment.service.transfer.validation.TransferValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.function.Function.identity;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferValidator transferValidator;
    private final TransferProcessor transferProcessor;
    private final NotificationService notificationService;

    public TransferResult transferFunds(TransferDTO transfer) {
        return transferValidator.validate(transfer).fold(identity(), this::processTransfer);
    }

    private TransferSuccessful processTransfer(TransferDTO r) {
        TransferAccountIds transferAccountIds = transferProcessor.process(r);
        notificationService.notify(transferAccountIds);
        return new TransferSuccessful();
    }

}

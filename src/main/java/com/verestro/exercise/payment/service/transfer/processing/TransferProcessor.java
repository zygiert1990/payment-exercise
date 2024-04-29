package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.TransferAccountIds;
import com.verestro.exercise.payment.model.TransferDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferProcessor {

    private final TransferProcessingDataFactory transferProcessingDataFactory;
    private final TransferProcessingStepFactory transferProcessingStepFactory;

    @Transactional
    public TransferAccountIds process(TransferDTO transfer) {
        TransferProcessingData processed = processTransfer(transfer);
        return new TransferAccountIds(processed.sourceAccount().id(), processed.targetAccount().id());
    }

    private TransferProcessingData processTransfer(TransferDTO transfer) {
        return transferProcessingStepFactory.processingSteps()
                .foldLeft(transferProcessingDataFactory.create(transfer), (data, step) -> step.process(data));
    }

}

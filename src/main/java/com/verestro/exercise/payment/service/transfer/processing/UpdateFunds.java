package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferCountDTO;
import org.springframework.stereotype.Component;

@Component
class UpdateFunds implements TransferProcessingStep {

    @Override
    public TransferProcessingData process(TransferProcessingData processingData) {
        AccountDTO updatedSource = updateSourceAccount(processingData);
        AccountDTO updatedTarget = updateTargetAccount(processingData);
        TransferCountDTO updatedTransferCount = processingData.sourceTransferCount().toBuilder().account(updatedSource).build();
        return new TransferProcessingData(updatedSource, updatedTarget, updatedTransferCount, processingData.transferAmount());
    }

    private AccountDTO updateSourceAccount(TransferProcessingData processingData) {
        return processingData.sourceAccount().toBuilder()
                .funds(processingData.sourceAccount().funds() - processingData.transferAmount())
                .build();
    }

    private AccountDTO updateTargetAccount(TransferProcessingData processingData) {
        return processingData.targetAccount().toBuilder()
                .funds(processingData.targetAccount().funds() + processingData.transferAmount())
                .build();
    }
}

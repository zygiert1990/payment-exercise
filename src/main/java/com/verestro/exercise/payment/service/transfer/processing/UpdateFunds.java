package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferCountDTO;
import org.springframework.stereotype.Component;

@Component
class UpdateFunds implements TransferProcessingStep {

    private static final int COUNT_STEP = 1;

    @Override
    public TransferProcessingData process(TransferProcessingData processingData) {
        AccountDTO updatedSource = updateSourceAccountFunds(processingData);
        AccountDTO updatedTarget = updateTargetAccountFunds(processingData);
        TransferCountDTO updatedTransferCount = increaseTransferCount(processingData.sourceTransferCount(), updatedSource);
        return new TransferProcessingData(updatedSource, updatedTarget, updatedTransferCount, processingData.transferAmount());
    }

    private AccountDTO updateSourceAccountFunds(TransferProcessingData processingData) {
        return processingData.sourceAccount().toBuilder()
                .funds(processingData.sourceAccount().funds() - processingData.transferAmount())
                .build();
    }

    private AccountDTO updateTargetAccountFunds(TransferProcessingData processingData) {
        return processingData.targetAccount().toBuilder()
                .funds(processingData.targetAccount().funds() + processingData.transferAmount())
                .build();
    }

    private TransferCountDTO increaseTransferCount(TransferCountDTO transferCount, AccountDTO updatedSource) {
        return transferCount.toBuilder()
                .account(updatedSource)
                .count(transferCount.count() + COUNT_STEP)
                .build();
    }

}

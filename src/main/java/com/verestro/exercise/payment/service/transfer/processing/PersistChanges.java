package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.service.AccountService;
import com.verestro.exercise.payment.service.TransferCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
class PersistChanges implements TransferProcessingStep {

    private final AccountService accountService;
    private final TransferCountService transferCountService;

    @Override
    public TransferProcessingData process(TransferProcessingData processingData) {
        transferCountService.save(processingData.sourceTransferCount());
        accountService.saveAll(Set.of(processingData.sourceAccount(), processingData.targetAccount()));
        return processingData;
    }

}

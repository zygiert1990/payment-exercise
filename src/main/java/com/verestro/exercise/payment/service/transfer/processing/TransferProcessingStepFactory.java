package com.verestro.exercise.payment.service.transfer.processing;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
class TransferProcessingStepFactory {

    private final UpdateFunds updateFunds;
    private final PersistChanges persistChanges;

    List<TransferProcessingStep> processingSteps() {
        return List.of(updateFunds, persistChanges);
    }

}

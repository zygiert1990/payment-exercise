package com.verestro.exercise.payment.service.transfer.processing;

@FunctionalInterface
interface TransferProcessingStep {
    TransferProcessingData process(TransferProcessingData processingData);
}

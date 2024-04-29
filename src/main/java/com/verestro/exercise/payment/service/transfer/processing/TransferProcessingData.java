package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferCountDTO;

public record TransferProcessingData(AccountDTO sourceAccount,
                                     AccountDTO targetAccount,
                                     TransferCountDTO sourceTransferCount,
                                     int transferAmount) {
}

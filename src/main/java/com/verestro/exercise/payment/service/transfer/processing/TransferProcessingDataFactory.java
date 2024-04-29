package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferCountDTO;
import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.service.AccountService;
import com.verestro.exercise.payment.service.TransferCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
class TransferProcessingDataFactory {

    private final AccountService accountService;
    private final TransferCountService transferCountService;

    TransferProcessingData create(TransferDTO transfer) {
        Map<String, AccountDTO> accountByNumber = getAccounts(transfer);
        return new TransferProcessingData(
                accountByNumber.get(transfer.getSourceAccount()),
                accountByNumber.get(transfer.getTargetAccount()),
                retrieveTransferCount(transfer),
                transfer.getAmount());
    }

    private Map<String, AccountDTO> getAccounts(TransferDTO transfer) {
        return accountService.findByAccountNumbers(Set.of(transfer.getSourceAccount(), transfer.getTargetAccount()))
                .stream()
                .collect(toMap(AccountDTO::accountNumber, identity()));
    }

    private TransferCountDTO retrieveTransferCount(TransferDTO transfer) {
        return transferCountService.findByAccountNumberAndDate(transfer.getSourceAccount(), LocalDate.now())
                .orElseGet(() -> TransferCountDTO.builder().count(0).date(LocalDate.now()).build());
    }

}

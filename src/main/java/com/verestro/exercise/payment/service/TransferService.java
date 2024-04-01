package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.*;
import com.verestro.exercise.payment.security.UsernameProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final UsernameProvider usernameProvider;
    private final TransferCountService transferCountService;

    public TransferResult transferFunds(TransferDTO transfer) {
        if (!accountService.existsByAccountNumberAndUsername(transfer.getSourceAccount(), usernameProvider.provideUsername())) {
            return new TransferFailure("Currently logged user is not owner of the source account!");
        }
        if (!accountService.existsByAccountNumber(transfer.getTargetAccount())) {
            return new TransferFailure("Target account does not exist in the system!");
        }
        return transferCountService.findByAccountNumberAndDate(transfer.getSourceAccount(), LocalDate.now())
                .map(tc -> {
                    if (tc.count() > 3) {
                        return new TransferFailure("Exceeded maximum number of transfers for account number: " + transfer.getSourceAccount());
                    }
                    if (tc.account().funds() - transfer.getAmount() < 0) {
                        return new TransferFailure("Not enough funds for perform a transfer!");
                    }
                    AccountDTO sourceAccount = tc.account();
                    AccountDTO sourceUpdated = sourceAccount.toBuilder()
                            .funds(tc.account().funds() - transfer.getAmount())
                            .build();
                    TransferCountDTO tcUpdated = tc.toBuilder()
                            .account(sourceUpdated)
                            .count(tc.count() + 1)
                            .build();
                    AccountDTO targetAccount = accountService.findByAccountNumber(transfer.getTargetAccount());
                    AccountDTO targetAccountUpdated = targetAccount.toBuilder().funds(targetAccount.funds() + transfer.getAmount()).build();
                    TransferAccountIds transferAccountIds = accountService.updateAccounts(targetAccountUpdated, tcUpdated);
                    // notify
                    return new TransferSuccessful();
                })
                .orElseGet(() -> {
                    AccountDTO sourceAccount = accountService.findByAccountNumber(transfer.getSourceAccount());
                    if (sourceAccount.funds() - transfer.getAmount() < 0) {
                        return new TransferFailure("Not enough funds for perform a transfer!");
                    }
                    AccountDTO sourceUpdated = sourceAccount.toBuilder()
                            .funds(sourceAccount.funds() - transfer.getAmount())
                            .build();
                    TransferCountDTO tcUpdated = TransferCountDTO.builder()
                            .account(sourceUpdated)
                            .count(1)
                            .build();
                    AccountDTO targetAccount = accountService.findByAccountNumber(transfer.getTargetAccount());
                    AccountDTO targetAccountUpdated = targetAccount.toBuilder().funds(targetAccount.funds() + transfer.getAmount()).build();
                    TransferAccountIds transferAccountIds = accountService.updateAccounts(targetAccountUpdated, tcUpdated);
                    // notify
                    return new TransferSuccessful();
                });
    }

}

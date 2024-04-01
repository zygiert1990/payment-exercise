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
    private final NotificationService notificationService;

    // todo I do not have time to fully refactor this code
    public TransferResult transferFunds(TransferDTO transfer) {
        // Additional condition, as I assumed that sourceAccountNumber has to be assigned to
        // currently logged user
        if (!accountService.existsByAccountNumberAndUsername(transfer.getSourceAccount(), usernameProvider.provideUsername())) {
            return new TransferFailure("Currently logged user is not owner of the source account!");
        }
        if (!accountService.existsByAccountNumber(transfer.getTargetAccount())) {
            return new TransferFailure("Target account does not exist in the system!");
        }
        return transferCountService.findByAccountNumberAndDate(transfer.getSourceAccount(), LocalDate.now())
                .map(tc -> transferFundsNextTimeADay(transfer, tc))
                .orElseGet(() -> transferFundsFirstTimeADay(transfer));
    }

    private TransferResult transferFundsNextTimeADay(TransferDTO transfer, TransferCountDTO tc) {
        if (tc.count() > 3) {
            return new TransferFailure("Exceeded maximum number of transfers for account number: " + transfer.getSourceAccount());
        }
        AccountDTO sourceAccount = tc.account();
        if (sourceAccount.funds() - transfer.getAmount() < 0) {
            return new TransferFailure("Not enough funds for perform a transfer!");
        }
        TransferAccountIds transferAccountIds = updateAccountsFunds(transfer, updateTransferCount(tc, updateSourceAccount(transfer, sourceAccount)));
        notificationService.notify(transferAccountIds);
        return new TransferSuccessful();
    }

    private TransferResult transferFundsFirstTimeADay(TransferDTO transfer) {
        AccountDTO sourceAccount = accountService.findByAccountNumber(transfer.getSourceAccount());
        if (sourceAccount.funds() - transfer.getAmount() < 0) {
            return new TransferFailure("Not enough funds for perform a transfer!");
        }
        TransferAccountIds transferAccountIds = updateAccountsFunds(transfer, createTransferCount(updateSourceAccount(transfer, sourceAccount)));
        notificationService.notify(transferAccountIds);
        return new TransferSuccessful();
    }

    private TransferAccountIds updateAccountsFunds(TransferDTO transfer, TransferCountDTO transferCount) {
        AccountDTO targetAccount = accountService.findByAccountNumber(transfer.getTargetAccount());
        AccountDTO targetAccountUpdated = targetAccount.toBuilder().funds(targetAccount.funds() + transfer.getAmount()).build();
        return accountService.updateAccountsFunds(targetAccountUpdated, transferCount);
    }

    private TransferCountDTO createTransferCount(AccountDTO sourceUpdated) {
        return TransferCountDTO.builder()
                .account(sourceUpdated)
                .date(LocalDate.now())
                .count(1)
                .build();
    }

    private TransferCountDTO updateTransferCount(TransferCountDTO transferCount, AccountDTO sourceUpdated) {
        return transferCount.toBuilder()
                .account(sourceUpdated)
                .count(transferCount.count() + 1)
                .build();
    }

    private AccountDTO updateSourceAccount(TransferDTO transfer, AccountDTO sourceAccount) {
        return sourceAccount.toBuilder()
                .funds(sourceAccount.funds() - transfer.getAmount())
                .build();
    }

}

package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.AccountMapper;
import com.verestro.exercise.payment.model.*;
import com.verestro.exercise.payment.persistence.model.AccountEntity;
import com.verestro.exercise.payment.persistence.repository.AccountRepository;
import com.verestro.exercise.payment.security.UsernameProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserService userService;
    private final UsernameProvider usernameProvider;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransferCountService transferCountService;

    public AccountNumber createAccount(AccountCode code) {
        return new AccountNumber(tryAssignAccountToUser(code).account().accountNumber());
    }

    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    public boolean existsByAccountNumberAndUsername(String accountNumber, String username) {
        return accountRepository.existsByAccountNumberAndUsername(accountNumber, username);
    }

    public AccountDTO findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::map)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Can not find account with number: " + accountNumber));
    }

    @Transactional
    public TransferAccountIds updateAccountsFunds(AccountDTO targetAccount, TransferCountDTO transferCount) {
        AccountEntity updatedTargetAccount = accountRepository.save(accountMapper.map(targetAccount));
        accountRepository.save(accountMapper.map(transferCount.account()));
        TransferCountDTO updatedTransferCount = transferCountService.save(transferCount);
        return new TransferAccountIds(updatedTransferCount.account().id(), updatedTargetAccount.getId());
    }

    private UserDTO tryAssignAccountToUser(AccountCode code) {
        String username = usernameProvider.provideUsername();
        if (userService.hasAccount(username)) {
            throw new ResponseStatusException(BAD_REQUEST, "User can have only one account!");
        } else {
            return userService.save(userService.findByUsername(username).toBuilder().account(account(code)).build());
        }
    }

    private AccountDTO account(AccountCode code) {
        return AccountDTO.builder()
                .funds(code.getValue())
                .accountNumber(randomNumeric(20))
                .build();
    }

}

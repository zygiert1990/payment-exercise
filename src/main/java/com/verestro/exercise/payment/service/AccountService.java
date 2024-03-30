package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.AccountCode;
import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.persistence.model.AccountEntity;
import com.verestro.exercise.payment.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountNumber createAccount(AccountCode code) {
        return new AccountNumber(saveAccount(account(code)).getAccountNumber());
    }

    private AccountEntity account(AccountCode code) {
        return AccountEntity.builder()
                .sum(code.getValue())
                .accountNumber(randomNumeric(20))
                .build();
    }

    private AccountEntity saveAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

}

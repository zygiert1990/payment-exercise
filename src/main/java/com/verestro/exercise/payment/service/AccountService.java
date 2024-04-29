package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.AccountMapper;
import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.persistence.model.AccountEntity;
import com.verestro.exercise.payment.persistence.repository.AccountFunds;
import com.verestro.exercise.payment.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public List<AccountDTO> saveAll(Set<AccountDTO> accounts) {
        return accountRepository.saveAll(toAccountEntities(accounts)).stream()
                .map(accountMapper::map)
                .toList();
    }

    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    public boolean existsByAccountNumberAndUsername(String accountNumber, String username) {
        return accountRepository.existsByAccountNumberAndUsername(accountNumber, username);
    }

    public Optional<AccountFunds> findFundsByAccountNumber(String accountNumber) {
        return accountRepository.findFundsByAccountNumber(accountNumber);
    }

    public List<AccountDTO> findByAccountNumbers(Set<String> accountNumbers) {
        return accountRepository.findByAccountNumbers(accountNumbers).stream()
                .map(accountMapper::map)
                .toList();
    }

    private List<AccountEntity> toAccountEntities(Set<AccountDTO> accounts) {
        return accounts.stream()
                .map(accountMapper::map)
                .toList();
    }

}

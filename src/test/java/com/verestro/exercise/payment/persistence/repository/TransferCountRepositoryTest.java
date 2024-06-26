package com.verestro.exercise.payment.persistence.repository;

import com.verestro.exercise.payment.persistence.model.AccountEntity;
import com.verestro.exercise.payment.persistence.model.TransferCountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransferCountRepositoryTest {

    private static final String ACCOUNT_NUMBER = "11111111111111111111";
    private static final LocalDate DATE = LocalDate.of(2024, 4, 1);

    @Autowired
    private TransferCountRepository transferCountRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldFindByAccountNumberAndDate() {
        // given
        transferCountRepository.save(createTransferCount(accountRepository.save(createAccount())));
        // when
        Optional<TransferCountEntity> result = transferCountRepository.findByAccountNumberAndDate(ACCOUNT_NUMBER, DATE);
        // then
        assertThat(result).isPresent();
    }

    private TransferCountEntity createTransferCount(AccountEntity account) {
        return TransferCountEntity.builder()
                .date(DATE)
                .count(1)
                .account(account)
                .build();
    }

    private AccountEntity createAccount() {
        return AccountEntity.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .funds(100)
                .build();
    }

}
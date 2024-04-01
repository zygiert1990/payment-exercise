package com.verestro.exercise.payment.persistence.repository;

import com.verestro.exercise.payment.persistence.model.AccountEntity;
import com.verestro.exercise.payment.persistence.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.verestro.exercise.payment.persistence.model.NotificationChannel.SMS;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    private static final String ACCOUNT_NUMBER = "11111111111111111111";
    private static final String USERNAME = "username";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldExistsByAccountNumberAndUsername() {
        // given
        userRepository.save(createUserWithAccount());
        // when
        boolean result = accountRepository.existsByAccountNumberAndUsername(ACCOUNT_NUMBER, USERNAME);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldExistsByAccountNumber() {
        // given
        userRepository.save(createUserWithAccount());
        // when
        boolean result = accountRepository.existsByAccountNumber(ACCOUNT_NUMBER);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void shouldFindByAccountNumber() {
        // given
        userRepository.save(createUserWithAccount());
        // when
        Optional<AccountEntity> result = accountRepository.findByAccountNumber(ACCOUNT_NUMBER);
        // then
        assertThat(result).isPresent();
    }

    private UserEntity createUserWithAccount() {
        return UserEntity.builder()
                .username("username")
                .password("password")
                .phoneNumber("123123123")
                .email("email@email.com")
                .preferredNotificationChannel(SMS)
                .account(AccountEntity.builder().accountNumber(ACCOUNT_NUMBER).funds(100).build())
                .build();
    }

}
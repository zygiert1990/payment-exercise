package com.verestro.exercise.payment.persistence.repository;

import com.verestro.exercise.payment.persistence.model.AccountEntity;
import com.verestro.exercise.payment.persistence.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.verestro.exercise.payment.persistence.model.NotificationChannel.SMS;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    private static final String PHONE_NUMBER = "123123123";
    private static final String EMAIL = "email@email.com";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldFindUserByAccountNumber() {
        // given
        userRepository.save(createUserWithAccount());
        Set<String> accountIds = getAccountIds();
        // when
        List<UserCommunication> result = userRepository.findByAccountIds(accountIds);
        // then
        assertThat(result).singleElement()
                .extracting(
                        UserCommunication::getNotificationChannel,
                        UserCommunication::getPhoneNumber,
                        UserCommunication::getEmail)
                .containsExactly(SMS.name(), PHONE_NUMBER, EMAIL);
    }

    private Set<String> getAccountIds() {
        return accountRepository.findAll().stream().map(AccountEntity::getId).collect(Collectors.toSet());
    }

    private UserEntity createUserWithAccount() {
        return UserEntity.builder()
                .username("username")
                .password("password")
                .phoneNumber(PHONE_NUMBER)
                .email(EMAIL)
                .preferredNotificationChannel(SMS)
                .account(AccountEntity.builder().accountNumber("11111111111111111111").funds(100).build())
                .build();
    }

}
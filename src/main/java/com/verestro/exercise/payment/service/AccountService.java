package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.AccountCode;
import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserService userService;

    public AccountNumber createAccount(AccountCode code) {
        return new AccountNumber(assignAccountToUser(code).account().accountNumber());
    }

    private UserDTO assignAccountToUser(AccountCode code) {
        UserDTO loggedUser = userService.getLoggedUser();
        if (loggedUser.account() != null) {
            throw new IllegalStateException("User can have only one account!");
        } else {
            return userService.save(loggedUser.toBuilder().account(account(code)).build());
        }
    }

    private AccountDTO account(AccountCode code) {
        return AccountDTO.builder()
                .sum(code.getValue())
                .accountNumber(randomNumeric(20))
                .build();
    }

}

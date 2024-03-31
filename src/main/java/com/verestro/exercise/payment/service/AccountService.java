package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.AccountCode;
import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.security.UsernameProvider;
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

    public AccountNumber createAccount(AccountCode code) {
        return new AccountNumber(tryAssignAccountToUser(code).account().accountNumber());
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
                .sum(code.getValue())
                .accountNumber(randomNumeric(20))
                .build();
    }

}

package com.verestro.exercise.payment.controller;

import com.verestro.exercise.payment.model.AccountCode;
import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.service.account.CreateAccountService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final CreateAccountService createAccountService;

    @PostMapping(value = "account")
    public ResponseEntity<AccountNumber> createAccount(@RequestParam @NotNull AccountCode code) {
        return ResponseEntity.ok(createAccountService.createAccount(code));
    }

}

package com.verestro.exercise.payment.controller;

import com.verestro.exercise.payment.model.AccountCode;
import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.service.AccountService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "account")
    public ResponseEntity<AccountNumber> createAccount(@RequestParam @NotNull AccountCode code) {
        return ResponseEntity.ok(accountService.createAccount(code));
    }

}

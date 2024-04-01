package com.verestro.exercise.payment.controller;

import com.verestro.exercise.payment.model.*;
import com.verestro.exercise.payment.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PutMapping(value = "transfer-funds")
    public ResponseEntity<String> register(@RequestBody @Valid TransferDTO transfer) {
        TransferResult result = transferService.transferFunds(transfer);
        return result instanceof TransferSuccessful ?
                ResponseEntity.ok(result.getMessage()) :
                ResponseEntity.badRequest().body(result.getMessage());
    }

}

package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.service.AccountService;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TargetAccountExistsTest {

    private final String TARGET_ACCOUNT = "TARGET_ACCOUNT";

    @Mock
    private AccountService accountService;
    @InjectMocks
    private TargetAccountExists rule;

    @Test
    void shouldPass() {
        // given
        when(accountService.existsByAccountNumber(eq(TARGET_ACCOUNT))).thenReturn(true);
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isRight()).isTrue();
    }

    @Test
    void shouldFail() {
        // given
        when(accountService.existsByAccountNumber(eq(TARGET_ACCOUNT))).thenReturn(false);
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    private Either<TransferResult, TransferDTO> validate() {
        return rule.validate(TransferDTO.builder().targetAccount(TARGET_ACCOUNT).build());
    }

}
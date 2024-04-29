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

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class HasEnoughFundsTest {

    private final String SOURCE_ACCOUNT = "SOURCE_ACCOUNT";
    private static final int TRANSFER_AMOUNT = 100;

    @Mock
    private AccountService accountService;
    @InjectMocks
    private HasEnoughFunds rule;

    @Test
    void shouldFailWhenCanNotFindFunds() {
        // given
        when(accountService.findFundsByAccountNumber(eq(SOURCE_ACCOUNT))).thenReturn(empty());
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    @Test
    void shouldFailWhenNotEnoughFunds() {
        // given
        when(accountService.findFundsByAccountNumber(eq(SOURCE_ACCOUNT))).thenReturn(of(() -> TRANSFER_AMOUNT - 1));
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    @Test
    void shouldPass() {
        // given
        when(accountService.findFundsByAccountNumber(eq(SOURCE_ACCOUNT))).thenReturn(of(() -> TRANSFER_AMOUNT));
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isRight()).isTrue();
    }

    private Either<TransferResult, TransferDTO> validate() {
        return rule.validate(TransferDTO.builder().amount(TRANSFER_AMOUNT).sourceAccount(SOURCE_ACCOUNT).build());
    }

}
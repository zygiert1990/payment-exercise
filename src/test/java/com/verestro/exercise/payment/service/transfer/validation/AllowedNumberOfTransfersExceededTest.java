package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.service.TransferCountService;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AllowedNumberOfTransfersExceededTest {

    private final String SOURCE_ACCOUNT = "SOURCE_ACCOUNT";

    @Mock
    private TransferCountService transferCountService;
    @InjectMocks
    private AllowedNumberOfTransfersExceeded rule;

    @Test
    void shouldPassForFirstTransfer() {
        // given
        when(transferCountService.findTransferCountByAccountNumberAndDate(eq(SOURCE_ACCOUNT), any())).thenReturn(empty());
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isRight()).isTrue();
    }

    @Test
    void shouldPassForThirdTransfer() {
        // given
        when(transferCountService.findTransferCountByAccountNumberAndDate(eq(SOURCE_ACCOUNT), any())).thenReturn(Optional.of(() -> 3));
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isRight()).isTrue();
    }

    @Test
    void shouldFail() {
        // given
        when(transferCountService.findTransferCountByAccountNumberAndDate(eq(SOURCE_ACCOUNT), any())).thenReturn(Optional.of(() -> 4));
        // when
        Either<TransferResult, TransferDTO> result = validate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    private Either<TransferResult, TransferDTO> validate() {
        return rule.validate(TransferDTO.builder().sourceAccount(SOURCE_ACCOUNT).build());
    }

}
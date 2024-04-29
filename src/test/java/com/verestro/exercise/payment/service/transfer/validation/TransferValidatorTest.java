package com.verestro.exercise.payment.service.transfer.validation;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferFailure;
import com.verestro.exercise.payment.model.TransferResult;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransferValidatorTest {

    private static final TransferDTO TRANSFER = TransferDTO.builder().build();
    private static final Either<TransferResult, TransferDTO> LEFT = Either.left(new TransferFailure(""));
    private static final Either<TransferResult, TransferDTO> RIGHT = Either.right(TRANSFER);

    @Mock
    private SourceAccountOwedByLoggedUser sourceAccountOwedByLoggedUser;
    @Mock
    private TargetAccountExists targetAccountExists;
    @Mock
    private AllowedNumberOfTransfersExceeded allowedNumberOfTransfersExceeded;
    @Mock
    private HasEnoughFunds hasEnoughFunds;

    @Test
    void shouldFailWhenSourceAccountNotOwedByLoggedUser() {
        // given
        when(sourceAccountOwedByLoggedUser.validate(any())).thenReturn(LEFT);
        // when
        Either<TransferResult, TransferDTO> result = getValidate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    @Test
    void shouldFailWhenTargetAccountDoesNotExist() {
        // given
        when(sourceAccountOwedByLoggedUser.validate(any())).thenReturn(RIGHT);
        when(targetAccountExists.validate(any())).thenReturn(LEFT);
        // when
        Either<TransferResult, TransferDTO> result = getValidate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    @Test
    void shouldFailWhenAllowedNumberOfTransfersExceeded() {
        // given
        when(sourceAccountOwedByLoggedUser.validate(any())).thenReturn(RIGHT);
        when(targetAccountExists.validate(any())).thenReturn(RIGHT);
        when(allowedNumberOfTransfersExceeded.validate(any())).thenReturn(LEFT);
        // when
        Either<TransferResult, TransferDTO> result = getValidate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    @Test
    void shouldFailWhenNotEnoughFunds() {
        // given
        when(sourceAccountOwedByLoggedUser.validate(any())).thenReturn(RIGHT);
        when(targetAccountExists.validate(any())).thenReturn(RIGHT);
        when(allowedNumberOfTransfersExceeded.validate(any())).thenReturn(RIGHT);
        when(hasEnoughFunds.validate(any())).thenReturn(LEFT);
        // when
        Either<TransferResult, TransferDTO> result = getValidate();
        // then
        assertThat(result.isLeft()).isTrue();
    }

    @Test
    void shouldPass() {
        // given
        when(sourceAccountOwedByLoggedUser.validate(any())).thenReturn(RIGHT);
        when(targetAccountExists.validate(any())).thenReturn(RIGHT);
        when(allowedNumberOfTransfersExceeded.validate(any())).thenReturn(RIGHT);
        when(hasEnoughFunds.validate(any())).thenReturn(RIGHT);
        // when
        Either<TransferResult, TransferDTO> result = getValidate();
        // then
        assertThat(result.isRight()).isTrue();
    }

    private Either<TransferResult, TransferDTO> getValidate() {
        return new TransferValidator(new TransferValidationRuleFactory(sourceAccountOwedByLoggedUser, targetAccountExists, allowedNumberOfTransfersExceeded, hasEnoughFunds))
                .validate(TRANSFER);
    }

}
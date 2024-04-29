package com.verestro.exercise.payment.service.transfer;

import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.model.TransferFailure;
import com.verestro.exercise.payment.model.TransferResult;
import com.verestro.exercise.payment.model.TransferSuccessful;
import com.verestro.exercise.payment.service.notification.NotificationService;
import com.verestro.exercise.payment.service.transfer.processing.TransferProcessor;
import com.verestro.exercise.payment.service.transfer.validation.TransferValidator;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransferServiceTest {

    @Mock
    private TransferValidator transferValidator;
    @Mock
    private TransferProcessor transferProcessor;
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private TransferService transferService;

    @Test
    void shouldReturnTransferFailureWhenValidationFailed() {
        // given
        when(transferValidator.validate(any())).thenReturn(Either.left(new TransferFailure("")));
        // when
        TransferResult result = transferFunds();
        // then
        assertThat(result).isInstanceOf(TransferFailure.class);
    }

    @Test
    void shouldReturnTransferSuccessfulWhenValidationPassed() {
        // given
        when(transferValidator.validate(any())).thenReturn(Either.right(TransferDTO.builder().build()));
        // when
        TransferResult result = transferFunds();
        // then
        assertThat(result).isInstanceOf(TransferSuccessful.class);
    }

    private TransferResult transferFunds() {
        return transferService.transferFunds(TransferDTO.builder().build());
    }

}
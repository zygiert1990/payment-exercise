package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferCountDTO;
import com.verestro.exercise.payment.model.TransferDTO;
import com.verestro.exercise.payment.service.AccountService;
import com.verestro.exercise.payment.service.TransferCountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TransferProcessingDataFactoryTest {

    private static final String ACCOUNT_1 = "ACCOUNT_1";
    private static final String ACCOUNT_2 = "ACCOUNT_2";
    private static final int TRANSFER_AMOUNT = 100;
    private static final int TRANSFER_COUNT = 2;
    private static final AccountDTO SOURCE_ACCOUNT = AccountDTO.builder().accountNumber(ACCOUNT_1).build();
    private static final AccountDTO TARGET_ACCOUNT = AccountDTO.builder().accountNumber(ACCOUNT_2).build();

    @Mock
    private AccountService accountService;
    @Mock
    private TransferCountService transferCountService;
    @InjectMocks
    private TransferProcessingDataFactory transferProcessingDataFactory;

    @BeforeEach
    void setup() {
        when(accountService.findByAccountNumbers(anySet())).thenReturn(List.of(SOURCE_ACCOUNT, TARGET_ACCOUNT));
    }

    @Test
    void shouldCreateDataForFirstTransferADay() {
        // given
        TransferDTO transfer = getTransfer();
        when(transferCountService.findByAccountNumberAndDate(eq(ACCOUNT_1), any())).thenReturn(of(TransferCountDTO.builder().count(TRANSFER_COUNT).build()));
        // when
        TransferProcessingData result = create(transfer);
        // then
        assertThat(result.sourceAccount()).isEqualTo(SOURCE_ACCOUNT);
        assertThat(result.targetAccount()).isEqualTo(TARGET_ACCOUNT);
        assertThat(result.transferAmount()).isEqualTo(TRANSFER_AMOUNT);
        assertThat(result.sourceTransferCount().count()).isEqualTo(TRANSFER_COUNT);
    }

    @Test
    void shouldCreateDataForNextTransferADay() {
        // given
        TransferDTO transfer = getTransfer();
        when(transferCountService.findByAccountNumberAndDate(eq(ACCOUNT_1), any())).thenReturn(empty());
        // when
        TransferProcessingData result = create(transfer);
        // then
        assertThat(result.sourceAccount()).isEqualTo(SOURCE_ACCOUNT);
        assertThat(result.targetAccount()).isEqualTo(TARGET_ACCOUNT);
        assertThat(result.transferAmount()).isEqualTo(TRANSFER_AMOUNT);
        assertThat(result.sourceTransferCount().count()).isEqualTo(0);
    }

    private TransferProcessingData create(TransferDTO transfer) {
        return transferProcessingDataFactory.create(transfer);
    }

    private TransferDTO getTransfer() {
        return TransferDTO.builder()
                .sourceAccount(ACCOUNT_1)
                .targetAccount(ACCOUNT_2)
                .amount(TRANSFER_AMOUNT)
                .build();
    }

}
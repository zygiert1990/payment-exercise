package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferCountDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateFundsTest {

    private static final int FUNDS = 100;
    private static final int TRANSFER_AMOUNT = 40;

    @Test
    void shouldUpdateSourceAccount() {
        // given
        TransferProcessingData data = getProcessingData();
        // when
        TransferProcessingData result = process(data);
        // then
        assertThat(result.sourceAccount().funds()).isEqualTo(60);
    }

    @Test
    void shouldUpdateTargetAccount() {
        // given
        TransferProcessingData data = getProcessingData();
        // when
        TransferProcessingData result = process(data);
        // then
        assertThat(result.targetAccount().funds()).isEqualTo(140);
    }

    @Test
    void shouldUpdateSourceTransferCount() {
        // given
        TransferProcessingData data = getProcessingData();
        // when
        TransferProcessingData result = process(data);
        // then
        assertThat(result.sourceTransferCount().count()).isEqualTo(1);
    }

    private TransferProcessingData process(TransferProcessingData data) {
        return new UpdateFunds().process(data);
    }

    private TransferProcessingData getProcessingData() {
        return TransferProcessingData.builder()
                .sourceAccount(getAccount())
                .targetAccount(getAccount())
                .transferAmount(TRANSFER_AMOUNT)
                .sourceTransferCount(TransferCountDTO.builder().count(0).build())
                .build();
    }

    private AccountDTO getAccount() {
        return AccountDTO.builder().funds(FUNDS).build();
    }

}
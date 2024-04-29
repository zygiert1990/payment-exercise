package com.verestro.exercise.payment.service.transfer.processing;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.model.TransferAccountIds;
import com.verestro.exercise.payment.model.TransferDTO;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransferProcessorTest {

    @Mock
    private TransferProcessingDataFactory transferProcessingDataFactory;
    @Mock
    private TransferProcessingStepFactory transferProcessingStepFactory;
    @InjectMocks
    private TransferProcessor transferProcessor;

    @Test
    void shouldProcess() {
        // given
        when(transferProcessingDataFactory.create(any())).thenReturn(getTransferProcessingData());
        when(transferProcessingStepFactory.processingSteps()).thenReturn(List.of(new TestStep(), new TestStep()));
        // when
        TransferAccountIds result = transferProcessor.process(TransferDTO.builder().build());
        // then
        assertThat(result.sourceAccountId()).isEqualTo("11");
        assertThat(result.targetAccountId()).isEqualTo("22");
    }

    private TransferProcessingData getTransferProcessingData() {
        return new TransferProcessingData(
                AccountDTO.builder().id("").build(),
                AccountDTO.builder().id("").build(),
                null,
                0
        );
    }

    private static final class TestStep implements TransferProcessingStep {

        @Override
        public TransferProcessingData process(TransferProcessingData processingData) {
            return new TransferProcessingData(
                    updateAccount(processingData.sourceAccount(), "1"),
                    updateAccount(processingData.targetAccount(), "2"),
                    processingData.sourceTransferCount(),
                    processingData.transferAmount());
        }

        private AccountDTO updateAccount(AccountDTO account, String character) {
            return account.toBuilder().id(account.id() + character).build();
        }
    }

}
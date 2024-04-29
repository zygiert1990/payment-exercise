package com.verestro.exercise.payment.it;

import com.verestro.exercise.payment.model.AccountNumber;
import com.verestro.exercise.payment.model.TransferDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.verestro.exercise.payment.it.TestFixtures.*;
import static com.verestro.exercise.payment.model.AccountCode.CODE_5;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferControllerTest extends BaseIntegrationTest {

    @BeforeEach
    void setup() throws Exception {
        registerUser();
        registerUser(anotherUser());
    }

    @Test
    void shouldPerformTransfer() throws Exception {
        // given
        TransferDTO transfer = getTransferDTO(100);
        // when
        MvcResult result = authenticatedPutWithBody(TRANSFER_FUNDS_URL, transfer, USERNAME);
        // then
        assertThatOkResponse(result);
    }

    @Test
    void shouldNotPerformTransfer() throws Exception {
        // given
        TransferDTO transfer = getTransferDTO(1000);
        // when
        MvcResult result = authenticatedPutWithBody(TRANSFER_FUNDS_URL, transfer, USERNAME);
        // then
        assertThatBadRequestResponse(result);
        assertThat(result.getResponse().getContentAsString()).isNotBlank();
    }

    private TransferDTO getTransferDTO(int transferAmount) throws Exception {
        return TransferDTO.builder()
                .sourceAccount(createAccount(USERNAME))
                .targetAccount(createAccount(USERNAME_2))
                .amount(transferAmount)
                .build();
    }

    private String createAccount(String username) throws Exception {
        return convertResponse(authenticatedPostWithParam(ACCOUNT_URL, getCreateAccountParams(CODE_5), username), AccountNumber.class).value();
    }

}

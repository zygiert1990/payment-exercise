package com.verestro.exercise.payment.it;

import com.verestro.exercise.payment.model.AccountNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MultiValueMap;

import static com.verestro.exercise.payment.model.AccountCode.CODE_1;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest extends BaseIntegrationTest {

    @BeforeEach
    void setup() throws Exception {
        registerUser();
    }

    @Test
    void shouldCreateAccount() throws Exception {
        // given
        MultiValueMap<String, String> params = getCreateAccountParams(CODE_1.name());
        // when
        MvcResult result = authenticatedPostWithParam(ACCOUNT_URL, params);
        // then
        assertThatOkResponse(result);
        assertThat(convertResponse(result, AccountNumber.class).value()).hasSize(20);
    }

    @Test
    void shouldNotCreateAccount() throws Exception {
        // given
        MultiValueMap<String, String> params = getCreateAccountParams((String) null);
        // when
        MvcResult result = authenticatedPostWithParam(ACCOUNT_URL, params);
        // then
        assertThatBadRequestResponse(result);
    }

}

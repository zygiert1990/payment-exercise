package com.verestro.exercise.payment.it;

import com.verestro.exercise.payment.model.UserId;
import com.verestro.exercise.payment.model.UserRegistrationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.verestro.exercise.payment.it.TestFixtures.validUser;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationControllerTest extends BaseIntegrationTest {

    @Test
    void shouldRegisterUser() throws Exception {
        // given
        UserRegistrationDTO user = validUser();
        // when
        MvcResult result = postWithBody(REGISTER_URL, user);
        // then
        assertThatOkResponse(result);
        assertThat(convertResponse(result, UserId.class).id()).isNotNull();
    }

    @Test
    void shouldNotRegisterUser() throws Exception {
        // given
        UserRegistrationDTO user = UserRegistrationDTO.builder().build();
        // when
        MvcResult result = postWithBody(REGISTER_URL, user);
        // then
        assertThatBadRequestResponse(result);
    }
}

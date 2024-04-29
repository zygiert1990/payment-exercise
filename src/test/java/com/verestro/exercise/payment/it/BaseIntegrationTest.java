package com.verestro.exercise.payment.it;

import com.google.gson.Gson;
import com.verestro.exercise.payment.model.AccountCode;
import com.verestro.exercise.payment.model.UserRegistrationDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static com.verestro.exercise.payment.it.TestFixtures.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.util.CollectionUtils.toMultiValueMap;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class BaseIntegrationTest {

    protected static final String ACCOUNT_URL = "/account";
    protected static final String REGISTER_URL = "/register";
    protected static final String TRANSFER_FUNDS_URL = "/transfer-funds";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    protected void registerUser() throws Exception {
        postWithBody(REGISTER_URL, validUser());
    }

    protected void registerUser(UserRegistrationDTO user) throws Exception {
        postWithBody(REGISTER_URL, user);
    }

    protected <T> MvcResult postWithBody(String url, T body) throws Exception {
        return mockMvc.perform(post(url)
                        .accept(APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(body)))
                .andReturn();
    }

    protected <T> MvcResult authenticatedPutWithBody(String url, T body, String username) throws Exception {
        return mockMvc.perform(put(url)
                        .accept(APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(body))
                        .with(httpBasic(username, PASSWORD)))
                .andReturn();
    }

    protected MvcResult authenticatedPostWithParam(String url, MultiValueMap<String, String> params) throws Exception {
        return authenticatedPostWithParam(url, params, USERNAME);
    }

    protected MvcResult authenticatedPostWithParam(String url, MultiValueMap<String, String> params, String username) throws Exception {
        return mockMvc.perform(post(url)
                        .accept(APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .queryParams(params)
                        .with(httpBasic(username, PASSWORD)))
                .andReturn();
    }

    protected <T> T convertResponse(MvcResult mvcResult, Class<T> classOfT) throws Exception {
        return gson.fromJson(mvcResult.getResponse().getContentAsString(), classOfT);
    }

    protected void assertThatOkResponse(MvcResult result) {
        assertThat(result.getResponse().getStatus()).isEqualTo(OK.value());
    }

    protected void assertThatBadRequestResponse(MvcResult result) {
        assertThat(result.getResponse().getStatus()).isEqualTo(BAD_REQUEST.value());
    }

    protected MultiValueMap<String, String> getCreateAccountParams(AccountCode code) {
        return getCreateAccountParams(code.name());
    }

    protected MultiValueMap<String, String> getCreateAccountParams(String code) {
        return toMultiValueMap(Map.of("code", singletonList(code)));
    }

}

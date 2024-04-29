package com.verestro.exercise.payment.it;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MultiValueMap;

import static com.verestro.exercise.payment.it.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class BaseIntegrationTest {

    protected static final String ACCOUNT_URL = "/account";
    protected static final String REGISTER_URL = "/register";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    protected void registerUser() throws Exception {
        postWithBody(REGISTER_URL, validUser());
    }

    protected <T> MvcResult postWithBody(String url, T body) throws Exception {
        return mockMvc.perform(post(url)
                        .accept(APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(body)))
                .andReturn();
    }

    protected MvcResult authenticatedPostWithParam(String url, MultiValueMap<String, String> params) throws Exception {
        return mockMvc.perform(post(url)
                        .accept(APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .queryParams(params)
                        .with(httpBasic(USERNAME, PASSWORD)))
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

}

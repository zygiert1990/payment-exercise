package com.verestro.exercise.payment.it;

import com.google.gson.Gson;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class IntegrationTestConfiguration {

    @Bean
    Gson gson() {
        return new Gson();
    }

}

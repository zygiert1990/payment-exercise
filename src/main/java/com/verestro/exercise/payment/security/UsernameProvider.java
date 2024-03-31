package com.verestro.exercise.payment.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsernameProvider {

    public String provideUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

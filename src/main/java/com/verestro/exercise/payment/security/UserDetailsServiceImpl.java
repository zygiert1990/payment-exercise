package com.verestro.exercise.payment.security;

import com.verestro.exercise.payment.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findCredentials(username)
                .map(user -> new User(user.getUsername(), user.getPassword(), emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("Can not retrieve user credentials for given username: " + username));
    }

}

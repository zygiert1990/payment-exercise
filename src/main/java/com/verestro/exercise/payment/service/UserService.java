package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.UserMapper;
import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.persistence.repository.UserRepository;
import com.verestro.exercise.payment.security.UsernameProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UsernameProvider usernameProvider;
    private final UserMapper userMapper;

    public UserDTO getLoggedUser() {
        return userRepository.findByUsername(usernameProvider.provideUsername())
                .map(userMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Can not get logged user!"));
    }

    public UserDTO save(UserDTO user) {
        return userMapper.map(userRepository.save(userMapper.map(user)));
    }

}

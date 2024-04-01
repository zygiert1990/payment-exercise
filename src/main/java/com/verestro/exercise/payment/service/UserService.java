package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.UserMapper;
import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::map)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Can not find user by username: " + username));
    }

    public UserDTO save(UserDTO user) {
        return userMapper.map(userRepository.save(userMapper.map(user)));
    }

    public boolean hasAccount(String username) {
        return userRepository.hasAccount(username);
    }

}

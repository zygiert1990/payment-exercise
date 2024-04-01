package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.UserMapper;
import com.verestro.exercise.payment.model.NotificationChannel;
import com.verestro.exercise.payment.model.UserCommunicationDTO;
import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.persistence.repository.UserCommunication;
import com.verestro.exercise.payment.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

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

    public List<UserCommunicationDTO> findByAccountIds(Set<String> accountIds) {
        return userRepository.findByAccountIds(accountIds)
                .stream()
                .map(this::toUserCommunicationDTO)
                .toList();
    }

    public UserDTO save(UserDTO user) {
        return userMapper.map(userRepository.save(userMapper.map(user)));
    }

    public boolean hasAccount(String username) {
        return userRepository.hasAccount(username);
    }

    private UserCommunicationDTO toUserCommunicationDTO(UserCommunication u) {
        return new UserCommunicationDTO(NotificationChannel.valueOf(u.getNotificationChannel()), u.getPhoneNumber(), u.getEmail());
    }

}

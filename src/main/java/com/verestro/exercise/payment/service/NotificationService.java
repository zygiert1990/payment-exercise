package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.TransferAccountIds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final NotificationProviderFactory notificationProviderFactory;

    public void notify(TransferAccountIds transferAccountIds) {
        userService.findByAccountIds(Set.of(transferAccountIds.sourceAccountId(), transferAccountIds.targetAccountId()))
                .forEach(user -> notificationProviderFactory.getProvider(user.notificationChannel()).notify(user));
    }

}

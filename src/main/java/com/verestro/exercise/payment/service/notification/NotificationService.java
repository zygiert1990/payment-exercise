package com.verestro.exercise.payment.service.notification;

import com.verestro.exercise.payment.model.TransferAccountIds;
import com.verestro.exercise.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Async
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

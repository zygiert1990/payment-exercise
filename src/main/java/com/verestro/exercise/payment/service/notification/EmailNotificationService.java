package com.verestro.exercise.payment.service.notification;

import com.verestro.exercise.payment.model.UserCommunicationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Async
public class EmailNotificationService implements NotificationProvider {
    @Override
    public void notify(UserCommunicationDTO user) {
        log.info("NOTIFY USER BY EMAIL! {}", user.email());
        log.info("THREAD ID: {}", Thread.currentThread().getId());
    }
}

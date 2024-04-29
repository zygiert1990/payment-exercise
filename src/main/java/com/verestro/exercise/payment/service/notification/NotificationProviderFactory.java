package com.verestro.exercise.payment.service.notification;

import com.verestro.exercise.payment.model.NotificationChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationProviderFactory {

    private final SmsNotificationService smsNotificationService;
    private final EmailNotificationService emailNotificationService;

    public NotificationProvider getProvider(NotificationChannel channel) {
        return switch (channel) {
            case SMS -> smsNotificationService;
            case EMAIL -> emailNotificationService;
        };
    }

}

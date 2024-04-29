package com.verestro.exercise.payment.service.notification;

import com.verestro.exercise.payment.model.UserCommunicationDTO;

public interface NotificationProvider {

    void notify(UserCommunicationDTO user);

}

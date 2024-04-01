package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.model.UserCommunicationDTO;

public interface NotificationProvider {

    void notify(UserCommunicationDTO user);

}

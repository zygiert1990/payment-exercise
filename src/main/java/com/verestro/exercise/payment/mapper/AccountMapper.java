package com.verestro.exercise.payment.mapper;

import com.verestro.exercise.payment.model.AccountDTO;
import com.verestro.exercise.payment.persistence.model.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO map(AccountEntity account);

    AccountEntity map(AccountDTO account);

}

package com.verestro.exercise.payment.mapper;

import com.verestro.exercise.payment.model.UserDTO;
import com.verestro.exercise.payment.persistence.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    UserDTO map(UserEntity user);

    UserEntity map(UserDTO user);

}

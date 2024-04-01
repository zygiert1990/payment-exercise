package com.verestro.exercise.payment.mapper;

import com.verestro.exercise.payment.model.TransferCountDTO;
import com.verestro.exercise.payment.persistence.model.TransferCountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface TransferCountMapper {

    TransferCountDTO map(TransferCountEntity transferCount);

    TransferCountEntity map(TransferCountDTO transferCount);

}

package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.TransferCountMapper;
import com.verestro.exercise.payment.model.TransferCountDTO;
import com.verestro.exercise.payment.persistence.repository.TransferCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferCountService {

    private final TransferCountRepository transferCountRepository;
    private final TransferCountMapper transferCountMapper;

    Optional<TransferCountDTO> findByAccountNumberAndDate(String accountNumber, LocalDate date) {
        return transferCountRepository.findByAccountNumberAndDate(accountNumber, date).map(transferCountMapper::map);
    }

    public TransferCountDTO save(TransferCountDTO transferCount) {
        return transferCountMapper.map(transferCountRepository.save(transferCountMapper.map(transferCount)));
    }

}

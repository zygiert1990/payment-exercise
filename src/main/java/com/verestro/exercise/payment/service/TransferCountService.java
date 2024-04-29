package com.verestro.exercise.payment.service;

import com.verestro.exercise.payment.mapper.TransferCountMapper;
import com.verestro.exercise.payment.model.TransferCountDTO;
import com.verestro.exercise.payment.persistence.repository.TransferCount;
import com.verestro.exercise.payment.persistence.repository.TransferCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class TransferCountService {

    private final TransferCountRepository transferCountRepository;
    private final TransferCountMapper transferCountMapper;

    public Optional<TransferCountDTO> findByAccountNumberAndDate(String accountNumber, LocalDate date) {
        return transferCountRepository.findByAccountNumberAndDate(accountNumber, date).map(transferCountMapper::map);
    }

    public Optional<TransferCount> findTransferCountByAccountNumberAndDate(String accountNumber, LocalDate date) {
        return transferCountRepository.findTransferCountByAccountNumberAndDate(accountNumber, date);
    }

    public TransferCountDTO save(TransferCountDTO transferCount) {
        return transferCountMapper.map(transferCountRepository.save(transferCountMapper.map(transferCount)));
    }

}

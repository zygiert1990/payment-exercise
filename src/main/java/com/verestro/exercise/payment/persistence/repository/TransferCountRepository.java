package com.verestro.exercise.payment.persistence.repository;

import com.verestro.exercise.payment.persistence.model.TransferCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TransferCountRepository extends JpaRepository<TransferCountEntity, String> {

    @Query("FROM TransferCountEntity tc JOIN FETCH tc.account a " +
            "where a.accountNumber = :accountNumber and tc.date = :date")
    Optional<TransferCountEntity> findByAccountNumberAndDate(@Param("accountNumber") String accountNumber, @Param("date") LocalDate date);

    @Query(value = "select tc.count from TRANSFER_COUNT tc join ACCOUNTS a where a.account_number = :accountNumber and tc.date = :date", nativeQuery = true)
    Optional<TransferCount> findTransferCountByAccountNumberAndDate(@Param("accountNumber") String accountNumber, @Param("date") LocalDate date);

}

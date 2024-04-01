package com.verestro.exercise.payment.persistence.repository;

import com.verestro.exercise.payment.persistence.model.AccountEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    @Query("select case when count(a) > 0 then true else false end " +
            "FROM AccountEntity a where a.accountNumber = :accountNumber")
    boolean existsByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query(value = "select case when count(a.id) > 0 then true else false end " +
            "FROM ACCOUNTS a join USERS u on a.id=u.account_id " +
            "where a.account_number = :accountNumber and u.username = :username",
            nativeQuery = true)
    boolean existsByAccountNumberAndUsername(@Param("accountNumber") String accountNumber, @Param("username") String username);

    @EntityGraph(attributePaths = "transferCounts")
    Optional<AccountEntity> findByAccountNumber(String accountNumber);

}

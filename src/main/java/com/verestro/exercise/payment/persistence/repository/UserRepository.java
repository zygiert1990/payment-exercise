package com.verestro.exercise.payment.persistence.repository;

import com.verestro.exercise.payment.persistence.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @EntityGraph(attributePaths = "account")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query(value = "select u.username, u.password from USERS u where u.username = :username", nativeQuery = true)
    Optional<UsernameCredentials> findCredentials(@Param("username") String username);

    @Query("select case when count(u) > 0 then true else false end " +
            "FROM UserEntity u where u.username = :username AND u.account IS NOT NULL")
    boolean hasAccount(@Param("username") String username);

}

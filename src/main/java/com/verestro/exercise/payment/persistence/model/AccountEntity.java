package com.verestro.exercise.payment.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "ACCOUNTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(unique = true, length = 20, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private int funds;

    @OneToMany(cascade = MERGE, fetch = LAZY)
    private Set<TransferCountEntity> transferCounts = new HashSet<>();

}

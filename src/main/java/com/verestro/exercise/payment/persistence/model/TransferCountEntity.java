package com.verestro.exercise.payment.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "TRANSFER_COUNT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransferCountEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int count;

    @ManyToOne(cascade = MERGE, fetch = LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

}

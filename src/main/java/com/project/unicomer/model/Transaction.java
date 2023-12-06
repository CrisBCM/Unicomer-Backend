package com.project.unicomer.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String fromClientName;
    private String toClientName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Card card;
}

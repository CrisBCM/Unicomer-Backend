package com.project.unicomer.dto;

import com.project.unicomer.model.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String ownerName;
    private BigDecimal amount;
    private LocalDateTime date;
    private String toClientName;
    private String transactionType;
    public TransactionDTO (Transaction transaction){
        ownerName = transaction.getCard().getCardHolder();
        amount = transaction.getAmount();
        date = transaction.getDate();
        toClientName = transaction.getToClientName();
        transactionType = transaction.getTransactionType().name();
    }
}

package com.project.unicomer.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Card {
    @Id
    private String number;

    private String cardHolder;
    private String thruDate;
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Client client;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
        transaction.setCard(this);
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setThruDate(String thruDate) {
        this.thruDate = thruDate;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}

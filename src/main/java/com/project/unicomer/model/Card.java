package com.project.unicomer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    private String cardHolder;
    private String number;
    private LocalDate thruDate;
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

}

package com.project.unicomer.dto;

import com.project.unicomer.model.Card;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CardDTO {
    private String number;
    private String cardHolder;
    private String thruDate;
    private BigDecimal balance;

    public CardDTO(Card card){
        this.number = card.getNumber();
        this.cardHolder = card.getCardHolder();
        this.thruDate = card.getThruDate();
        this.balance = card.getBalance();
    }
}

package com.project.unicomer.service;

import com.project.unicomer.model.Card;

public interface CardService {
    public String getRandomNumberCard();
    public boolean existsByNumber(String number);
    public Card findByNumber(String number);
}

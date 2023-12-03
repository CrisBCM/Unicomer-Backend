package com.project.unicomer.service.implement;

import com.project.unicomer.repository.CardRepository;
import com.project.unicomer.service.CardService;
import com.project.unicomer.util.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Override
    public String getRandomNumberCard() {

        String cardNumber = "";

        do {
            cardNumber = CardUtils.getCardNumber();
        }while(cardRepository.existsByNumber(cardNumber));

        return cardNumber;
    }
}

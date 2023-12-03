package com.project.unicomer.repository;

import com.project.unicomer.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    public boolean existsByNumber(String cardNumber);
    public Card findByNumber(String number);
}

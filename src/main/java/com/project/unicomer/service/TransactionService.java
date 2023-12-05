package com.project.unicomer.service;

import com.project.unicomer.model.TransactionTypeInfo;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    public ResponseEntity<Object> transfer(String fromCard, String toCard, BigDecimal amount);
    public void deposit(String numberCard, BigDecimal amount);
    public void withdrawal(String numberCard, BigDecimal amount);
    public List<TransactionTypeInfo> getTransactionsType();
}

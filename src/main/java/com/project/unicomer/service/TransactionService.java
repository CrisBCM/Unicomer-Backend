package com.project.unicomer.service;

import com.project.unicomer.dto.TransactionDTO;
import com.project.unicomer.model.TransactionTypeInfo;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    public ResponseEntity<Object> transfer(String fromCard, String toCard, BigDecimal amount);
    public ResponseEntity<Object> deposit(String toCardNumber, BigDecimal amount);
    public ResponseEntity<Object> withdrawal(String fromCardNumber, BigDecimal amount);
    public List<TransactionTypeInfo> getTransactionsType();
    public List<TransactionDTO> getMyTransactions(String cardNumber);

}

package com.project.unicomer.service;

import com.project.unicomer.dto.EgressDTO;
import com.project.unicomer.dto.IncomeDTO;
import com.project.unicomer.dto.TransactionDTO;
import com.project.unicomer.model.Transaction;
import com.project.unicomer.model.TransactionTypeInfo;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    public ResponseEntity<Object> transfer(String fromCard, String toCard, BigDecimal amount);
    public ResponseEntity<Object> deposit(String toCardNumber, BigDecimal amount);
    public ResponseEntity<Object> withdrawal(String fromCardNumber, BigDecimal amount);
    public List<TransactionTypeInfo> getTransactionsType();
    public List<TransactionDTO> getMyTransactions(String cardNumber);
    public BigDecimal calculateMonthlyIncomeEgress(List<Transaction> transactions);
    public boolean isTransactionAddition(String currentClient, String toClientName);
    public IncomeDTO getMonthlyIncome(Authentication authentication);
    public EgressDTO getMonthlyEgress(Authentication authentication);
}

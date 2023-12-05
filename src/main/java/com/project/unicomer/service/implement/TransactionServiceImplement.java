package com.project.unicomer.service.implement;

import com.project.unicomer.model.Card;
import com.project.unicomer.model.Transaction;
import com.project.unicomer.model.TransactionType;
import com.project.unicomer.model.TransactionTypeInfo;
import com.project.unicomer.repository.CardRepository;
import com.project.unicomer.service.CardService;
import com.project.unicomer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepository cardRepository;
    @Override
    public ResponseEntity<Object> transfer(String fromCardNumber, String toCardNumber, BigDecimal amount) {

        Card fromCard = cardService.findByNumber(fromCardNumber);
        Card toCard = cardService.findByNumber(toCardNumber);

        if(amount.compareTo(BigDecimal.ZERO) == 0) return new ResponseEntity<>("El monto o la descripcion estan vacios!", HttpStatus.FORBIDDEN);
        if(fromCardNumber == null || toCardNumber == null) return new ResponseEntity<>("Numeros de las tarjetas vacios!", HttpStatus.FORBIDDEN);
        if(fromCardNumber.equals(toCardNumber)) return new ResponseEntity<>("Los numeros de las tarjetas son iguales!", HttpStatus.FORBIDDEN);
        if(!cardService.existsByNumber(fromCardNumber)) return new ResponseEntity<>("El numero de tarjeta no existe!", HttpStatus.FORBIDDEN);
        if(fromCard.getBalance().compareTo(amount) < 0) return new ResponseEntity<>("Monto insuficiente!", HttpStatus.FORBIDDEN);

        Transaction debitTransaction = Transaction.builder()
                .transactionType(TransactionType.TRANSACCION)
                .date(LocalDateTime.now())
                .amount(amount.negate())
                .build();

        fromCard.addTransaction(debitTransaction);

        Transaction creditTransaction = Transaction.builder()
                .transactionType(TransactionType.TRANSACCION)
                .date(LocalDateTime.now())
                .amount(amount)
                .card(toCard)
                .build();

        toCard.addTransaction(creditTransaction);

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public void deposit(String numberCard, BigDecimal amount) {

    }

    @Override
    public void withdrawal(String numberCard, BigDecimal amount) {

    }

    @Override
    public List<TransactionTypeInfo> getTransactionsType() {
        return Arrays.stream(TransactionType.values())
                .map(transactionType -> new TransactionTypeInfo(transactionType.name()))
                .collect(Collectors.toList());
    }
}

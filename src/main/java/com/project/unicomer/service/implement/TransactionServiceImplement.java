package com.project.unicomer.service.implement;

import com.project.unicomer.Const;
import com.project.unicomer.dto.EgressDTO;
import com.project.unicomer.dto.IncomeDTO;
import com.project.unicomer.dto.TransactionDTO;
import com.project.unicomer.model.*;
import com.project.unicomer.repository.CardRepository;
import com.project.unicomer.repository.ClientRepository;
import com.project.unicomer.repository.TransactionRepository;
import com.project.unicomer.service.CardService;
import com.project.unicomer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public ResponseEntity<Object> transfer(String fromCardNumber, String toCardNumber, BigDecimal amount) {

        Card fromCard = cardService.findByNumber(fromCardNumber);
        Card toCard = cardService.findByNumber(toCardNumber);

        if(amount.compareTo(BigDecimal.ZERO) == 0) return new ResponseEntity<>("El monto esta vacio!", HttpStatus.FORBIDDEN);
        if(fromCardNumber == null || toCardNumber == null) return new ResponseEntity<>("Numeros de las tarjetas vacios!", HttpStatus.FORBIDDEN);
        if(fromCardNumber.equals(toCardNumber)) return new ResponseEntity<>("Los numeros de las tarjetas son iguales!", HttpStatus.FORBIDDEN);
        if(!cardService.existsByNumber(fromCardNumber)) return new ResponseEntity<>("El numero de tarjeta no existe!", HttpStatus.FORBIDDEN);
        if(fromCard.getBalance().compareTo(amount) < 0) return new ResponseEntity<>("Monto insuficiente!", HttpStatus.FORBIDDEN);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TRANSACCION)
                .fromClientName(fromCard.getCardHolder())
                .date(LocalDateTime.now(Const.ZONA_HORARIA_ARGENTINA))
                .amount(amount)
                .toClientName(toCard.getCardHolder())
                .build();

        fromCard.addTransaction(transaction);
        toCard.addTransaction(transaction);

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));



        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        TransactionDTO savedTransaction = new TransactionDTO(transactionRepository.save(transaction));

        return new ResponseEntity<>(savedTransaction,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> deposit(String toCardNumber, BigDecimal amount) {

        if(!cardRepository.existsByNumber(toCardNumber))
            return new ResponseEntity<>("El numero de tarjeta no existe!", HttpStatus.FORBIDDEN);
        if(amount.compareTo(BigDecimal.ZERO) == 0)
            return new ResponseEntity<>("El monto esta vacio!", HttpStatus.FORBIDDEN);

        Card toCard = cardRepository.findByNumber(toCardNumber);

        Transaction deposit = Transaction.builder()
                .transactionType(TransactionType.DEPOSITO)
                .date(LocalDateTime.now(Const.ZONA_HORARIA_ARGENTINA))
                .amount(amount)
                .build();

        toCard.setBalance(toCard.getBalance().add(amount));
        toCard.addTransaction(deposit);

        TransactionDTO savedTransaction = new TransactionDTO(transactionRepository.save(deposit));
        cardRepository.save(toCard);

        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> withdrawal(String fromCardNumber, BigDecimal amount) {

        if(!cardRepository.existsByNumber(fromCardNumber))
            return new ResponseEntity<>("El numero de tarjeta no existe!", HttpStatus.FORBIDDEN);

        if(amount.compareTo(BigDecimal.ZERO) == 0)
            return new ResponseEntity<>("El monto esta vacio!", HttpStatus.FORBIDDEN);

        Card fromCard = cardRepository.findByNumber(fromCardNumber);

        Transaction withdrawal = Transaction.builder()
                .transactionType(TransactionType.EXTRACCION)
                .date(LocalDateTime.now(Const.ZONA_HORARIA_ARGENTINA))
                .amount(amount)
                .build();

        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        fromCard.addTransaction(withdrawal);
        TransactionDTO savedTransaction = new TransactionDTO(transactionRepository.save(withdrawal));
        cardRepository.save(fromCard);

        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @Override
    public List<TransactionTypeInfo> getTransactionsType() {
        return Arrays.stream(TransactionType.values())
                .map(transactionType -> new TransactionTypeInfo(transactionType.name()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getMyTransactions(String cardNumber) {
        return cardRepository.findByNumber(cardNumber).getTransactions()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateMonthlyIncomeEgress(List<Transaction> transactions) {
        BigDecimal incomeEgress = new BigDecimal(0);
        LocalDate currentMonth = LocalDate.now(Const.ZONA_HORARIA_ARGENTINA);

        for (Transaction transaction : transactions) {
            if(transaction.getDate().getMonth() == currentMonth.getMonth() && transaction.getDate().getYear() == currentMonth.getYear()){
                incomeEgress = incomeEgress.add(transaction.getAmount());
            }

        }
        return incomeEgress;
    }

    @Override
    public boolean isTransactionAddition(String currentClient, String toClientName) {

        return currentClient.equals(toClientName);
    }


    @Override
    public IncomeDTO getMonthlyIncome(Authentication authentication) {

        Client currentClient = clientRepository.findByDni(authentication.getName()).orElse(null);
        String clientName = currentClient.getFullName();

        List<Transaction> transactions = currentClient.getCards().stream().findFirst().orElse(null).getTransactions();

        List<Transaction> transactionsIncome = transactions.stream().filter( transaction -> transaction.getTransactionType() == TransactionType.DEPOSITO || transaction.getTransactionType() == TransactionType.TRANSACCION
                && isTransactionAddition(clientName, transaction.getToClientName())).toList();

        BigDecimal monthlyIncome = calculateMonthlyIncomeEgress(transactionsIncome);

        return IncomeDTO.builder()
                .monthlyIncome(monthlyIncome)
                .name("Ingreso")
                .build();
    }

    @Override
    public EgressDTO getMonthlyEgress(Authentication authentication) {
        Client currentClient = clientRepository.findByDni(authentication.getName()).orElse(null);
        String clientName = currentClient.getFullName();
        List<Transaction> transactions = currentClient.getCards().stream().findFirst().orElse(null).getTransactions();
        List<Transaction> transactionsEgress = transactions.stream().filter( transaction -> transaction.getTransactionType() == TransactionType.EXTRACCION || transaction.getTransactionType() == TransactionType.TRANSACCION
                && !(isTransactionAddition(clientName, transaction.getToClientName()))).toList();

        BigDecimal monthlyEgress = calculateMonthlyIncomeEgress(transactionsEgress);

        return EgressDTO.builder()
                .monthlyEgress(monthlyEgress)
                .name("Egreso")
                .build();
    }

}

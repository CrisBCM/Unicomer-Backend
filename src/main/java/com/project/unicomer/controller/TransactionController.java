package com.project.unicomer.controller;

import com.project.unicomer.model.TransactionTypeInfo;
import com.project.unicomer.model.TransferRequest;
import com.project.unicomer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/transactions-type")
    public List<TransactionTypeInfo> getTransactionsType(){
        return transactionService.getTransactionsType();
    }
    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<Object> transfer(@RequestBody TransferRequest transferRequest){
        return transactionService.transfer(transferRequest.getFromCardNumber(), transferRequest.getToCardNumber(), transferRequest.getAmount());
    }
}

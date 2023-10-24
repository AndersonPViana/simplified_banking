package com.simplifiedBanking.controllers;

import com.simplifiedBanking.domain.transaction.Transaction;
import com.simplifiedBanking.dtos.TransactionDTO;
import com.simplifiedBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Transaction newTransaction = this.service.createTransaction(transactionDTO);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
}

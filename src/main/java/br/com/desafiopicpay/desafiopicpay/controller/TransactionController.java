package br.com.desafiopicpay.desafiopicpay.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafiopicpay.desafiopicpay.dto.TransactionDTO;
import br.com.desafiopicpay.desafiopicpay.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public void createTransaction(@RequestBody TransactionDTO transactionDTO){
        transactionService.createTransaction(transactionDTO);
    }

}

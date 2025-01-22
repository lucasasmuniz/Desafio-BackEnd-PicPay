package br.com.desafiopicpay.desafiopicpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafiopicpay.desafiopicpay.dto.DepositDTO;
import br.com.desafiopicpay.desafiopicpay.model.Deposit;
import br.com.desafiopicpay.desafiopicpay.service.DepositService;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    
    @Autowired
    private DepositService depositService;

    @PostMapping
    public ResponseEntity<Deposit> createDeposit(@RequestBody DepositDTO depositDTO){  
        Deposit newDeposit = depositService.createDeposit(depositDTO);
        return new ResponseEntity<>(newDeposit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Deposit>> findAllDeposits(){
        List<Deposit> deposits = depositService.findAllDeposits();
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }
}

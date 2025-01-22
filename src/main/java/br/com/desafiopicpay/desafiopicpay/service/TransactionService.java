package br.com.desafiopicpay.desafiopicpay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafiopicpay.desafiopicpay.dto.TransactionDTO;
import br.com.desafiopicpay.desafiopicpay.model.Transaction;
import br.com.desafiopicpay.desafiopicpay.model.User;
import br.com.desafiopicpay.desafiopicpay.model.UserType;
import br.com.desafiopicpay.desafiopicpay.repository.TransactionRepository;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizerService authorizerService;

    @Transactional
    public void createTransaction(TransactionDTO transactionDTO){
        // buscar usuarios
        User payee = this.userService.findUserById(transactionDTO.idPayee());
        User payer = this.userService.findUserById(transactionDTO.idPayer());
        
        // valida transacao
        validateTransaction(payee, transactionDTO.value());
        boolean authorizerResponse = this.authorizerService.authorizeTransaction(payee, transactionDTO.value());
        if (!authorizerResponse){
            throw new RuntimeException("Transaction Unauthorized");
        }
        
        // Alterar valores de saldo dos usuarios envolvidos
        payee.setBalance(payee.getBalance().subtract(transactionDTO.value()));
        payer.setBalance(payer.getBalance().add(transactionDTO.value()));
        
        // Criar transacao
        Transaction transaction = new Transaction();
        transaction.setPayee(payee);
        transaction.setPayer(payer);
        transaction.setAmount(transactionDTO.value());
        transaction.setCreatedAt(LocalDateTime.now());
        
        // salvar alteracao
        userService.saveUser(payer);
        userService.saveUser(payee);
        transactionRepository.save(transaction);
    }

    public void validateTransaction(User payee, BigDecimal amount){
        if (payee.getUserType() == UserType.RETAILER) {
            throw new RuntimeException("Retailers cannot make transfers");
        };

        if (payee.getBalance().compareTo(amount) == 0){
            throw new RuntimeException("User does not have sufficient balance to complete the transaction");
        }
    }
}

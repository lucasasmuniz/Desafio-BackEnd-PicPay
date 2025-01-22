package br.com.desafiopicpay.desafiopicpay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafiopicpay.desafiopicpay.dto.TransactionDTO;
import br.com.desafiopicpay.desafiopicpay.exception.ValidadeTransactionException;
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

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Transaction createTransaction(TransactionDTO transactionDTO){

        if (transactionDTO.idPayee() == transactionDTO.idPayer()) {
            throw new ValidadeTransactionException("It is not possible to transfer to yourself");
        }
        // buscar usuarios
        User payee = this.userService.findUserById(transactionDTO.idPayee());
        User payer = this.userService.findUserById(transactionDTO.idPayer());
        
        // valida transacao
        validateTransaction(payee, transactionDTO.value());
        boolean authorizerResponse = this.authorizerService.authorizeTransaction(payee, transactionDTO.value());
        if (!authorizerResponse){
            throw new ValidadeTransactionException("Transaction Unauthorized");
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

        this.notificationService.sendNotification(payee, "Transaction completed");
        this.notificationService.sendNotification(payer, "Transaction completed");

        return transaction;

    }

    public void validateTransaction(User payee, BigDecimal amount){
        if (payee.getUserType() == UserType.RETAILER) {
            throw new ValidadeTransactionException("Retailers cannot make transfers");
        };

        if (payee.getBalance().compareTo(amount) < 0){
            throw new ValidadeTransactionException("User does not have sufficient balance to complete the transaction");
        }
    }

    public List<Transaction> findAllTransactions(){
        return transactionRepository.findAll();
    }
}

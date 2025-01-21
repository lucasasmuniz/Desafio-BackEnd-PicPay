package br.com.desafiopicpay.desafiopicpay.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafiopicpay.desafiopicpay.model.User;
import br.com.desafiopicpay.desafiopicpay.model.UserType;
import br.com.desafiopicpay.desafiopicpay.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user){
        this.userRepository.save(user);
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public void validateTransaction(User payee, BigDecimal amount){
        if (payee.getUserType() == UserType.COMMON) {
            throw new RuntimeException("Retailers cannot make transfers");
        };

        if (payee.getBalance().compareTo(amount) == 0){
            throw new RuntimeException("User does not have sufficient balance to complete the transaction");
        }
    }
}

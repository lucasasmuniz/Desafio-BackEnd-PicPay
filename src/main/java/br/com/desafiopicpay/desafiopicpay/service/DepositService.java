package br.com.desafiopicpay.desafiopicpay.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafiopicpay.desafiopicpay.dto.DepositDTO;
import br.com.desafiopicpay.desafiopicpay.model.Deposit;
import br.com.desafiopicpay.desafiopicpay.model.User;
import br.com.desafiopicpay.desafiopicpay.repository.DepositRepository;
import jakarta.transaction.Transactional;

@Service
public class DepositService {
    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Deposit createDeposit(DepositDTO depositDTO){
        User user = this.userService.findUserById(depositDTO.idUser());

        Deposit newDeposit = new Deposit();
        newDeposit.setAmount(depositDTO.value());
        newDeposit.setUser(user);
        newDeposit.setCreatedAt(LocalDateTime.now());

        user.setBalance(user.getBalance().add(depositDTO.value()));
        userService.saveUser(user);

        return depositRepository.save(newDeposit);
    }

    public List<Deposit> findAllDeposits(){
        return this.depositRepository.findAll();
    }
}

package br.com.desafiopicpay.desafiopicpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafiopicpay.desafiopicpay.model.Deposit;

@Repository
public interface DepositRepository extends JpaRepository<Deposit,Long>{

    
}

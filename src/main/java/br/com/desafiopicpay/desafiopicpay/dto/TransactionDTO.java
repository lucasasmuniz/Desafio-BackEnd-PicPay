package br.com.desafiopicpay.desafiopicpay.dto;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long idPayee, Long idPayer) {
    public TransactionDTO(BigDecimal value, Long idPayee, Long idPayer){
        this.value = value;
        this.idPayee = idPayee;
        this.idPayer = idPayer;

        if (this.idPayee == this.idPayer) {
            throw new RuntimeException("It is not possible to transfer to yourself");
        }
    }
}

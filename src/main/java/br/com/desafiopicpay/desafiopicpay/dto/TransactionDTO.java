package br.com.desafiopicpay.desafiopicpay.dto;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long idPayee, Long idPayer) {
    
}

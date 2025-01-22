package br.com.desafiopicpay.desafiopicpay.dto;

import java.math.BigDecimal;

public record DepositDTO(BigDecimal value, Long idUser) {
    
}

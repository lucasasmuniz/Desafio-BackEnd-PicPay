package br.com.desafiopicpay.desafiopicpay.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.desafiopicpay.desafiopicpay.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="db_transection")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_payee")
    private User payee;
    @ManyToOne
    @JoinColumn(name = "id_payer")
    private User payer;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}

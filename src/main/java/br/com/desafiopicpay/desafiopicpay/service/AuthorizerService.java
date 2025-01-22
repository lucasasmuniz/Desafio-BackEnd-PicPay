package br.com.desafiopicpay.desafiopicpay.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.desafiopicpay.desafiopicpay.model.User;

@Service
public class AuthorizerService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean authorizeTransaction(User payee, BigDecimal amount){
        ResponseEntity<Map> response = this.restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().get("status").equals("success");
        } else return false;
    }
}
package br.com.desafiopicpay.desafiopicpay.service;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.desafiopicpay.desafiopicpay.dto.NotificationDTO;
import br.com.desafiopicpay.desafiopicpay.model.User;

@Service
public class NotificationService {
    
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message){

        NotificationDTO notificationRequest = new NotificationDTO(user.getEmail(), message);
        ResponseEntity<String> response = this.restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error sending the notification");         
        }
    }
}

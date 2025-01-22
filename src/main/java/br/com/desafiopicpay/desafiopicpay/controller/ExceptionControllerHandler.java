package br.com.desafiopicpay.desafiopicpay.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.desafiopicpay.desafiopicpay.dto.ExceptionDTO;
import br.com.desafiopicpay.desafiopicpay.exception.NotificationErrorExcepcion;
import br.com.desafiopicpay.desafiopicpay.exception.UserNotFoundException;
import br.com.desafiopicpay.desafiopicpay.exception.ValidadeTransactionException;

@RestControllerAdvice
public class ExceptionControllerHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> duplicatedTableFields(DataIntegrityViolationException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO("400", "User already exists");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO("400", e.getMessage());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(NotificationErrorExcepcion.class)
    public ResponseEntity<Object> notificationError(NotificationErrorExcepcion e){
        ExceptionDTO exceptionDTO = new ExceptionDTO("400", e.getMessage());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(ValidadeTransactionException.class)
    public ResponseEntity<Object> validadeTransactionError(ValidadeTransactionException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO("400", e.getMessage());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
}

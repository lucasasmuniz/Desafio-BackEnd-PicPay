package br.com.desafiopicpay.desafiopicpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafiopicpay.desafiopicpay.service.UserService;
import br.com.desafiopicpay.desafiopicpay.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }

}

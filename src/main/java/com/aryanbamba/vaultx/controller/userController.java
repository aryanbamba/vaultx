package com.aryanbamba.vaultx.controller;

import com.aryanbamba.vaultx.entity.User;
import com.aryanbamba.vaultx.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {

    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Welcome to vaultx";
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }
}

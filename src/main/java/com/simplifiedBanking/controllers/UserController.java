package com.simplifiedBanking.controllers;

import com.simplifiedBanking.domain.user.User;
import com.simplifiedBanking.dtos.UserDTO;
import com.simplifiedBanking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO userDTO) {
        User newUser = service.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.service.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}

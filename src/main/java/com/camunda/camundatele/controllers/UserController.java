/*
package com.camunda.camundatele.controllers;

import com.camunda.camundatele.entities.Users;
import com.camunda.camundatele.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersService service;

    public UserController(UsersService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users saved = service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}

*/

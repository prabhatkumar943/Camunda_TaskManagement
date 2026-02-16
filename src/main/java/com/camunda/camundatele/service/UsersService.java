/*
package com.camunda.camundatele.service;

import com.camunda.camundatele.entities.Users;
import com.camunda.camundatele.repository.UsersRespository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class UsersService {
    private final UsersRespository usersRepository;

    public UsersService(UsersRespository userRepository)
    {
        this.usersRepository=userRepository;
    }
    public Users createUser(Users user) {

        if (usersRepository.existsByAgentCode(user.getAgentCode())) {
            throw new RuntimeException("Agent already exists");
        }


        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }




}
*/

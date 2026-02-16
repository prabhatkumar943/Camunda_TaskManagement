/*
package com.camunda.camundatele.service;

import com.camunda.camundatele.entities.UserTask;
import com.camunda.camundatele.repository.UserTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;



    public UserTaskService(UserTaskRepository userTaskRepository){
        this.userTaskRepository=userTaskRepository;
    }

    public UserTask createUserTask(UserTask userTask){
        try {
        return  this.userTaskRepository.save(userTask);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
*/

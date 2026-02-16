package com.camunda.camundatele.config;

import io.camunda.client.CamundaClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PeocessAutoDeployjava {


    private  CamundaClient camundaClient;

    @Autowired
    public PeocessAutoDeployjava(CamundaClient camundaClient) {
        this.camundaClient = camundaClient;
    }
 Logger logger =   LoggerFactory.getLogger(PeocessAutoDeployjava.class);
    //@PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public void deploy() {

        logger.info("Deploying BPMN via CamundaClient (gRPC)");

        camundaClient
                .newDeployResourceCommand()
                .addResourceFromClasspath("bpmn/backend-telecaller.bpmn")
                .send()
                .join();

        logger.info("BPMN deployed successfully");
    }
}

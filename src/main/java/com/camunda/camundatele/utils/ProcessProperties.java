package com.camunda.camundatele.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties(prefix = "camunda.process")
@Getter
@Setter
public class ProcessProperties {

    @Value("${camunda.process.resource}")
    private String bpmnFile;
    @Value("${camunda.process.id}")
    private String id;
    @Value("${camunda.auth.oidc.auth-url}")
    private   String CAMUNDA_AUTH_URL;
    @Value("${camunda.auth.oidc.client-id}")
    private  String CAMUNDA_CLIENT_ID;
    @Value("${camunda.auth.oidc.client-secret}")
    private  String CAMUNDA_CLIENT_SECRET;
    @Value("${camunda.auth.oidc.audience}")
    private  String CAMUNDA_AUDIENCE;
    @Value("${camunda.client.zeebe.gateway-address}")
    private  String CAMUNDA_GRPC_ADDRESS;
    @Value("${camunda.client.rest.base-url}")
    private  String CAMUNDA_REST_ADDRESS;

    public String getBpmnFile() {
        return bpmnFile;
    }

    public void setBpmnFile(String bpmnFile) {
        this.bpmnFile = bpmnFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProcessProperties{" +
                "bpmnFile='" + bpmnFile + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}


package com.camunda.camundatele.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties(prefix = "camunda.process")
public class ProcessProperties {

    @Value("${camunda.process.resource}")
    private String bpmnFile;
    @Value("${camunda.process.id}")
    private String id;

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


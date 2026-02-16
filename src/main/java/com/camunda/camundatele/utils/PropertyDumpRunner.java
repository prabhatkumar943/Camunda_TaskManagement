/*
package com.camunda.camundatele.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PropertyDumpRunner {

    @Value("${camunda.client.zeebe.gateway.address:#{null}}")
    private String camundaGatewayAddress;

    @Value("${zeebe.client.gateway.address:#{null}}")
    private String zeebeGatewayAddress;

    @Value("${camunda.client.zeebe.security.plaintext:#{null}}")
    private String camundaPlaintext;

    @Value("${zeebe.client.security.plaintext:#{null}}")
    private String zeebePlaintext;

    @PostConstruct
    public void dump() {
        System.out.println("PROPERTY DUMP: camunda.client.zeebe.gateway.address = " + camundaGatewayAddress);
        System.out.println("PROPERTY DUMP: camunda.client.zeebe.security.plaintext = " + camundaPlaintext);
        System.out.println("PROPERTY DUMP: zeebe.client.gateway.address = " + zeebeGatewayAddress);
        System.out.println("PROPERTY DUMP: zeebe.client.security.plaintext = " + zeebePlaintext);
    }
}
*/

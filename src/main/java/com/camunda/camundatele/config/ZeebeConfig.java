/*
package com.camunda.camundatele.config;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZeebeConfig {

    @Value("${camunda.client.zeebe.gateway-address}")
    private String gatewayAddress;

    @Value("${camunda.client.zeebe.security.plaintext:false}")
    private boolean usePlaintextConnection;

    @Bean
    public ZeebeClient zeebeClient() {
        final ZeebeClientBuilder builder = ZeebeClient.newClientBuilder()
                .gatewayAddress(gatewayAddress)
                .usePlaintext();

        if (usePlaintextConnection) {
            builder.usePlaintext();
        }

        return builder.build();
    }
}
*/

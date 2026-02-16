package com.camunda.camundatele.config;

import io.camunda.client.CamundaClient;
import io.camunda.client.CredentialsProvider;
import io.camunda.client.impl.oauth.OAuthCredentialsProviderBuilder;
//above imports supports camunda-client-java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;


@Configuration
public class CamundaClientConfig {


    @Bean
    public CamundaClient camundaClient() {

        CredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(

                                        "http://localhost:18080/auth/realms/camunda-platform/protocol/openid-connect/token"

                        )
                        .clientId("orchestration")
                        .clientSecret("secret")
                        .audience("orchestration-api")
                        .build();

        CamundaClient client =
                CamundaClient.newClientBuilder()
                        .grpcAddress(URI.create("http://localhost:26500"))
                        .restAddress(URI.create("http://localhost:8088"))
                        .credentialsProvider(credentialsProvider)
                        .build();
        return client;
    }

   /* @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use String serialization for keys
        template.setKeySerializer(new StringRedisSerializer());
        // Use JSON serialization for values (requires Jackson)
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }*/
}


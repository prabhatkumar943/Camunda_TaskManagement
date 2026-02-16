package com.camunda.camundatele.config;

import com.camunda.camundatele.utils.ProcessProperties;
import io.camunda.client.CamundaClient;
import io.camunda.client.CredentialsProvider;
import io.camunda.client.impl.oauth.OAuthCredentialsProviderBuilder;
//above imports supports camunda-client-java

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;

import static org.slf4j.Logger.*;


@Configuration
public class CamundaClientConfig {

    private final ProcessProperties properties;
   Logger logger= LoggerFactory.getLogger(CamundaClientConfig.class.getName());
/*    @Value("${camunda.auth.oidc.auth-url}")
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
    private  String CAMUNDA_REST_ADDRESS;*/

    public CamundaClientConfig(ProcessProperties properties){
        this.properties = properties;
    }

    @Bean
    public CamundaClient camundaClient() {

        logger.info("Camunda client id 44 "+ properties.getCAMUNDA_CLIENT_ID());
        logger.info("Camunda auth url 45 "+ properties.getCAMUNDA_AUTH_URL());
        logger.info("Camunda audience 46 "+ properties.getCAMUNDA_AUDIENCE());
        logger.info("Camunda grpc address 47 "+ properties.getCAMUNDA_GRPC_ADDRESS());
        logger.info("Camunda rest address 48 "+ properties.getCAMUNDA_REST_ADDRESS());

        CredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(

                                        //"http://localhost:18080/auth/realms/camunda-platform/protocol/openid-connect/token"
                                properties.getCAMUNDA_AUTH_URL()
                        )
                        .clientId(properties.getCAMUNDA_CLIENT_ID())
                        .clientSecret(properties.getCAMUNDA_CLIENT_SECRET())
                        .audience(properties.getCAMUNDA_AUDIENCE())
                        .build();

        CamundaClient client =
                CamundaClient.newClientBuilder()
                        .grpcAddress(URI.create(properties.getCAMUNDA_GRPC_ADDRESS()))
                        .restAddress(URI.create(properties.getCAMUNDA_REST_ADDRESS()))
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


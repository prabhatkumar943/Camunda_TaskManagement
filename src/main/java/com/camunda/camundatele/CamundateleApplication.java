package com.camunda.camundatele;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
@ConfigurationPropertiesScan
public class CamundateleApplication {
	private static final Logger logger = LoggerFactory.getLogger(CamundateleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CamundateleApplication.class, args);
		logger.info("Camunda Tele started");
	}

	@Bean
	public WebClient webClient() {
		return WebClient.builder().build();
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	/*@Bean
	public ZeebeClient zeebeClient() {
		return ZeebeClient.newClientBuilder().build();
	}*/
}

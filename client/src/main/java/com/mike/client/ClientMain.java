package com.mike.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mike.Amount;
import com.mike.Transaction;
import com.mike.User;


@SpringBootApplication
public class ClientMain {
    
    private String SERVER1_PORT_ARG = "server1.port";
    private String SERVER1_USER_NAME = "server1.name";
    
    @Value("${server1.port}")
    private String SERVER1_PORT_VALUE;
    
    @Value("${server1.name}")
    private String SERVER1_NAME_VALUE;
    
    private String SERVER2_PORT_ARG = "server2.port";
    private String SERVER2_USER_NAME = "server2.name";
    
    @Value("${server2.port}")
    private String SERVER2_PORT_VALUE;
    
    @Value("${server2.name}")
    private String SERVER2_NAME_VALUE;

    private Logger logger = LoggerFactory.getLogger(ClientMain.class);
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClientMain.class);
        app.setBannerMode(Mode.OFF);
        app.run(args);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    
    @Bean
    @Order(1)
    ApplicationRunner run() {
        return args -> {
            logger.info("App start with option names: {} ", args.getOptionNames());
            List<String> userName = args.getOptionValues(SERVER1_USER_NAME);
            Set<String> errorMessages = new HashSet<>();
            if (CollectionUtils.isEmpty(userName)) {
                errorMessages.add("No value specified for " + SERVER1_USER_NAME);
            } 
            
            userName = args.getOptionValues(SERVER2_USER_NAME);
            if (CollectionUtils.isEmpty(userName)) {
                errorMessages.add("No value specified for " + SERVER2_USER_NAME);
            } 
            
            List<String> port = args.getOptionValues(SERVER1_PORT_ARG);
            if (CollectionUtils.isEmpty(port)) {
                errorMessages.add("No value specified for " + SERVER1_PORT_ARG);
            } 
            
            port = args.getOptionValues(SERVER2_PORT_ARG);
            if (CollectionUtils.isEmpty(port)) {
                errorMessages.add("No value specified for " + SERVER2_PORT_ARG);
            } 
            
            if (!CollectionUtils.isEmpty(errorMessages)) {
                String message = String.join("\n", errorMessages);
                throw new RuntimeException(message);
            }
        };
    }
    
    @Bean
    @Order(2)
    ApplicationRunner run(RestTemplate restTemplate) {
        return args -> {
           ObjectMapper mapper = new ObjectMapper();
           User user1 = new User(SERVER1_NAME_VALUE);
           User user2 = new User(SERVER2_NAME_VALUE);
           
           Transaction transaction1 = new Transaction(user1, user2, new Amount(100.0, "USD"));
           logger.info("Sending {} to {} Trustline user {}", transaction1, user1);
           
           String jsonString = mapper.writeValueAsString(transaction1);
           
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
           restTemplate.postForEntity("http://localhost:" + SERVER1_PORT_VALUE + "/send", entity, ResponseEntity.class);
 
           logger.info("Sending {} to Trustline user {}", transaction1, user2);
           restTemplate.postForEntity("http://localhost:" + SERVER2_PORT_VALUE + "/send", entity, ResponseEntity.class);
           
           Transaction transction2 = new Transaction(user2, user1, new Amount(100.0, "USD"));
           jsonString = mapper.writeValueAsString(transction2);
           entity = new HttpEntity<>(jsonString, headers);
           logger.info("Sending {} to {} Trustline user {}", transction2, user1);
           restTemplate.postForEntity("http://localhost:" + SERVER1_PORT_VALUE + "/send", entity, ResponseEntity.class);
           
           logger.info("Sending {} to {} Trustline user {}", transction2, user2);
           restTemplate.postForEntity("http://localhost:" + SERVER2_PORT_VALUE + "/send", entity, ResponseEntity.class);
           
           User chewie = new User("Chewie");
           Transaction transaction3 = new Transaction(user1, chewie, new Amount(100000.0, "USD"));
           jsonString = mapper.writeValueAsString(transaction3);
           entity = new HttpEntity<>(jsonString, headers);
           logger.info("Sending {} which has a user that shouldn't exist", transaction3);
           
           try {
              ResponseEntity<?> responseEntity = restTemplate.postForEntity("http://localhost:" + SERVER1_PORT_VALUE + "/send", entity, ResponseEntity.class);
           } catch (RestClientException e) {
               logger.error("Failed to send to " + chewie.getName(), e);
           }

        };
    }

}

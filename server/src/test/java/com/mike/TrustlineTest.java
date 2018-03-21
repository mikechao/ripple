package com.mike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mike.config.ActiveMQBrokerConfig;
import com.mike.exception.UserNotFoundException;
import com.mike.exception.UsersAreSameException;
import com.mike.service.UserService;
import com.mike.startup.Main;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,
    classes=Main.class,
    properties = {
            "user=Bob"
    }
)
public class TrustlineTest {

    private final Logger logger = LoggerFactory.getLogger(TrustlineTest.class);
    
    @Value("${local.server.port}")
    int localPort;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @MockBean
    private UserService mockUserService;
    
    private User bob = new User("Bob");
    private User alice = new User("Alice");
    private ObjectMapper mapper = new ObjectMapper();

    
    private static ActiveMQBrokerConfig brokerConfig = new ActiveMQBrokerConfig();
    
    @BeforeClass
    public static void beforeClass() {
        brokerConfig.start();
    }
    
    @AfterClass
    public static void afterClass() {
        brokerConfig = null;
    }
    
    private String getSendURL() {
        return String.format("http://localhost:%d/send", localPort);
    }
    
    @Test
    public void testTransactionWithSameUser() throws JsonProcessingException {
        Transaction sameUser = new Transaction(bob, bob, new Amount(10.0, "USD"));
        
        HttpEntity<String> entity = createEntity(sameUser);
        
        when(this.mockUserService.userExists(bob)).thenReturn(Boolean.TRUE);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(getSendURL(), entity, String.class);
            UsersAreSameException exception = new UsersAreSameException(sameUser);
            String body = response.getBody();
            assertEquals("The error messages did NOT match", exception.getMessage(), body);
        } catch (Exception e) {
            logger.error("Failed to handle a transaction with same users", e);
            fail("Failed to handle a transaction with same users" + e.getMessage());
        }
    }
    
    @Test
    public void testUserNotFound() throws JsonProcessingException {
        Transaction notFound = new Transaction(bob, alice, new Amount(100.0, "USD"));
        
        HttpEntity<String> entity = createEntity(notFound);
        
        when(this.mockUserService.userExists(bob)).thenReturn(Boolean.TRUE);
        when(this.mockUserService.userExists(alice)).thenReturn(Boolean.FALSE);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(getSendURL(), entity, String.class);
            String body = response.getBody();
            
            UserNotFoundException exception = new UserNotFoundException(alice, notFound);
            assertEquals("The error message did NOT match", exception.getMessage(), body);
        } catch (Throwable t) {
            logger.error("Failed to handle a transaction where user does not exist", t);
            fail("Failed to handle a transaction where user does not exist " + t.getMessage());
        }
    }
    
    @Test
    public void testRegularFromTransaction() throws JsonProcessingException {
        Transaction transaction = new Transaction(bob, alice, new Amount(1000.0, "USD"));
        
        HttpEntity<String> entity = createEntity(transaction);
        
        when(this.mockUserService.userExists(bob)).thenReturn(Boolean.TRUE);
        when(this.mockUserService.userExists(alice)).thenReturn(Boolean.TRUE);
        when(this.mockUserService.getCurrentUser()).thenReturn(bob);

        try {
            ResponseEntity<?> response = restTemplate.postForEntity(getSendURL(), entity, ResponseEntity.class);
            assertEquals("The http status did NOT match", HttpStatus.ACCEPTED, response.getStatusCode());
        } catch (Throwable t) {
            logger.error("Failed to process a correct transaction", t);
            fail("Failed to handle a transaction where user does not exist " + t.getMessage());
        }
        
    }
    
    @Test
    public void testRegularToTransaction() throws JsonProcessingException {
        Transaction transaction = new Transaction(alice, bob, new Amount(1000.0, "USD"));
        
        HttpEntity<String> entity = createEntity(transaction);
        
        when(this.mockUserService.userExists(bob)).thenReturn(Boolean.TRUE);
        when(this.mockUserService.userExists(alice)).thenReturn(Boolean.TRUE);
        when(this.mockUserService.getCurrentUser()).thenReturn(bob);

        try {
            ResponseEntity<?> response = restTemplate.postForEntity(getSendURL(), entity, ResponseEntity.class);
            assertEquals("The http status did NOT match", HttpStatus.ACCEPTED, response.getStatusCode());
        } catch (Throwable t) {
            logger.error("Failed to process a correct transaction", t);
            fail("Failed to handle a transaction where user does not exist " + t.getMessage());
        }
        
    }
    
    private HttpEntity<String> createEntity(Transaction transaction) throws JsonProcessingException {
        String json = mapper.writeValueAsString(transaction);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }
}

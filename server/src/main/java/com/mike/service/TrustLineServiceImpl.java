package com.mike.service;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mike.Amount;
import com.mike.Balance;
import com.mike.Transaction;
import com.mike.User;
import com.mike.routes.QueueNames;

@Service
public class TrustLineServiceImpl implements TrustLineService {

    private final Logger logger = LoggerFactory.getLogger(TrustLineServiceImpl.class);

    private final UserService userService;
    private final CamelContext camelContext;
    private final ProducerTemplate producerTemplate;

    private Balance balance = new Balance();

    @Autowired
    public TrustLineServiceImpl(UserService userService, CamelContext camelContext) {
        this.userService = userService;
        this.camelContext = camelContext;
        this.producerTemplate = camelContext.createProducerTemplate();
    }

    @PostConstruct
    public void init() {
        logger.info("Welcome to the Trustline");
        logger.info("Trustline balace is {}", balance);
    }

    @Override
    /**
     * Start the transaction 
     * 1. Check if the users exists first 
     * 2. Check if to and from users are same or not
     * 3. Create and Send a message to the topic "transaction"
     */
    public void startTransaction(Transaction transaction) throws Exception {
        // 1
        checkUser(transaction.getFromUser());
        checkUser(transaction.getToUser());

        // 2
        if (transaction.getFromUser().equals(transaction.getToUser())) {
            throw new Exception("From user and to user are the same");
        }
        
        // 3
        Exchange toUserExchange = ExchangeBuilder
                .anExchange(camelContext)
                .withBody(transaction)
                .build();

        producerTemplate.send(QueueNames.TRANSACTION_TOPIC, toUserExchange);
    }

    private void checkUser(User userToCheck) throws Exception {
        if (!userService.userExists(userToCheck)) {
            throw new Exception("The user " + userToCheck + " does not exist");
        }
    }

    @Override
    /**
     * handle the transaction where the current user is the from part.
     * Called by Camel after reading from the topic "transaction"
     * and filtered
     */
    public void handleFromTransaction(Transaction transaction) {
        logger.info("Execute Client Send from {} to {}...", transaction.getFromUser().getName(), transaction.getToUser().getName());
        Amount amount = transaction.getAmount();
        logger.info("Paying {} to {}...", amount, transaction.getToUser().getName());
        balance.subtract(amount);
        logger.info("Trustline balance is: {}", balance.getBalance());
    }

    @Override
    /**
     * Handle the transaction where the current user is the to part.
     * Called by Camel after reading from the topic "transaction"
     * and filtered
     */
    public void handleToTransaction(Transaction transaction) {
        logger.info("Execute Client Send from {} to {}...", transaction.getFromUser().getName(), transaction.getToUser().getName());
        Amount amount = transaction.getAmount();
        logger.info("You were paid {}!", amount);
        balance.add(amount);
        logger.info("Trustline balance is: {}", balance.getBalance());

    }
}

package com.mike.config;

import java.net.BindException;

import org.apache.activemq.broker.BrokerService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQBrokerConfig {

    private final String BROKER_URL = "tcp://localhost:61616";
    private final Logger logger = LoggerFactory.getLogger(ActiveMQBrokerConfig.class);

    public ActiveMQBrokerConfig() {

    }

    public void start() {
        BrokerService broker = new BrokerService();
        try {
            broker.addConnector("tcp://localhost:61616");
            broker.setPersistent(false);
            broker.start();
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            // ignore BindException because assuming
            // that the other server is already started
            if (!(rootCause instanceof BindException)) {
                logger.error("Failed to start broker", e);
            }
        }
    }

    public String getBrokerURL() {
        return BROKER_URL;
    }
}

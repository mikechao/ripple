package com.mike.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Java configuration for JMS and Apache Camel
 * 
 * @author MikeChao
 *
 */
@Configuration
public class JmsConfig {

    @Bean
    public ConnectionFactory pooledConnectionFactory() {
        ActiveMQBrokerConfig brokerConfig = new ActiveMQBrokerConfig();
        String url = brokerConfig.getBrokerURL();
        ActiveMQConnectionFactory activeMQCF = new ActiveMQConnectionFactory(url);
        activeMQCF.setTrustAllPackages(true);
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(activeMQCF);
        pooledConnectionFactory.setMaxConnections(8);
        return pooledConnectionFactory;
    }

    @Bean
    public JmsConfiguration jmsConfiguration() {
        JmsConfiguration jmsConfig = new JmsConfiguration(pooledConnectionFactory());
        jmsConfig.setConcurrentConsumers(1);
        return jmsConfig;
    }

    @Bean
    public ActiveMQComponent activeMQ() {
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(jmsConfiguration());
        component.setTransacted(true);
        component.setCacheLevelName("CACHE_CONSUMER");
        return component;
    }
}

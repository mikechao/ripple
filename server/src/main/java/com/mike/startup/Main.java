package com.mike.startup;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

import com.mike.User;
import com.mike.config.ActiveMQBrokerConfig;
import com.mike.service.UserService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.mike" })
public class Main {

    private static final String USER_ARG = "-Duser";

    @Value("${user}")
    String user;

    public static void main(String[] args) {

        ActiveMQBrokerConfig brokerConfig = new ActiveMQBrokerConfig();
        brokerConfig.start();

        SpringApplication.run(Main.class, args);
    }

    @Bean
    ApplicationRunner runner(@Autowired UserService userService, @Autowired CamelContext camelContext,
            @Value("${user}") String user) {

        return args -> {
            if (StringUtils.isEmpty(user)) {
                throw new RuntimeException("argument " + USER_ARG + " is missing");
            }
            // set the current user first since UserRouteBuilder depends
            // on having the current user set already
            userService.setCurrentUser(new User(user));

            camelContext.startAllRoutes();
        };
    }

}

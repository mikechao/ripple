package com.mike.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mike.service.UserService;

@Component
public class UserServiceRouteBuilder extends RouteBuilder {

    @Autowired
    UserService userService;

    @Override
    public void configure() throws Exception {
        from(QueueNames.USER_CHECKER_TIMER_QUEUE)
            .routeId(RouteIds.USER_CHECK_ROUTE)
            .autoStartup(false)
            .bean(userService, "getCurrentUser")
            .to(QueueNames.CURRENT_USER_TOPIC);

        from(QueueNames.CURRENT_USER_TOPIC)
            .routeId(RouteIds.CURRENT_USER_ROUTE)
            .autoStartup(false)
            .bean(userService, "addRemoteUser");
    }

}

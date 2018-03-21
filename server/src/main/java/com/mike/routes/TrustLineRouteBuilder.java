package com.mike.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mike.Transaction;
import com.mike.service.TrustLineService;
import com.mike.service.UserService;

@Component
public class TrustLineRouteBuilder extends RouteBuilder {

    @Autowired
    UserService userService;

    @Autowired
    TrustLineService trustLineService;

    @Override
    public void configure() throws Exception {
        from(QueueNames.TRANSACTION_TOPIC)
            .routeId(RouteIds.TO_USER_TRANSACTION_ROUTE)
            .autoStartup(false)
            .filter(new Predicate() {

                @Override
                public boolean matches(Exchange exchange) {
                    // filter the messages so that the correct method is called in TrustLineService
                    boolean match = false;
                    final Transaction transaction = exchange.getIn().getBody(Transaction.class);
                    if (transaction.getToUser().getName().equals(userService.getCurrentUser().getName())) {
                        match = true;
                    }
                    return match;
                }
            })
            .bean(trustLineService, "handleToTransaction");

        from(QueueNames.TRANSACTION_TOPIC)
            .routeId(RouteIds.FROM_USER_TRANSACTION_ROUTE)
            .autoStartup(false)
            .filter(new Predicate() {

                @Override
                public boolean matches(Exchange exchange) {
                    // filter the messages so that the correct method is called in TrustLineService
                    boolean match = false;
                    final Transaction transaction = exchange.getIn().getBody(Transaction.class);
                    if (transaction.getFromUser().getName().equals(userService.getCurrentUser().getName())) {
                        match = true;
                    }
                    return match;
                }
            })
            .bean(trustLineService, "handleFromTransaction");

    }

}

package com.mike.routes;

/**
 * A class to hold queue and topic name constants that are used
 * @author MikeChao
 *
 */
public final class QueueNames {

    private QueueNames() {
        // hide the constructor since just holding constants
    }

    public static final String USER_CHECKER_TIMER_QUEUE = "timer:userChecker?period=1000&delay=0";

    public static final String CURRENT_USER_TOPIC = "activemq:topic:currentUsers";

    public static final String TRANSACTION_TOPIC = "activemq:topic:transaction";
}

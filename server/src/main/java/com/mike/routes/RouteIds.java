package com.mike.routes;

/**
 * A class to hold constants that are used to name routes used by Camel
 * @author MikeChao
 *
 */
public final class RouteIds {

    private RouteIds() {
        // hide the constructor since just holding constants
    }

    /**
     * The name of the route that gets the current user and publishes to the topic
     * activemq:topic:currentUsers
     */
    public static final String USER_CHECK_ROUTE = "userCheckerRoute";

    /**
     * The name of the route that adds the remote user
     */
    public static final String CURRENT_USER_ROUTE = "currentUsersRoute";

    /**
     * The name of the route that handles to transactions
     */
    public static final String TO_USER_TRANSACTION_ROUTE = "toUserTransactionRoute";

    /**
     * The name of the route that handles from transactions
     */
    public static final String FROM_USER_TRANSACTION_ROUTE = "fromuUserTransactionRoute";

}

package com.mike.service;

import com.mike.Transaction;
import com.mike.routes.TrustLineRouteBuilder;

public interface TrustLineService {

    /**
     * Handles the transaction for the current user where they are the "from" part
     * of the transaction Method is called by Camel via
     * {@link TrustLineRouteBuilder}
     * 
     * @param transaction
     *            - The transaction where the current user is the "from" part
     */
    public void handleFromTransaction(Transaction transaction);

    /**
     * Handles the transaction for the current user where they are the "to" part of
     * the transaction Method is called by Camel via @{link
     * {@link TrustLineRouteBuilder}
     * 
     * @param transaction
     *            - The transaction where the current user is the "to" part
     */
    public void handleToTransaction(Transaction transaction);

    /**
     * Starts the process of paying money from 1 user to the other<br>
     * Method is called by {@link TrustLineController}
     * 
     * @param transaction
     *            - The transaction to be processed
     * @throws Exception
     *             - If the transaction contains incorrect information<br>
     *             For example if the 2 users involved in the transaction are the
     *             same
     */
    public void startTransaction(Transaction transaction);

}

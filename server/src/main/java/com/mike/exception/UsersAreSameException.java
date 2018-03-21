package com.mike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mike.Transaction;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsersAreSameException extends RuntimeException {
    private static final long serialVersionUID = -3879654806315445858L;

    private final Transaction transaction;
    
    public UsersAreSameException(Transaction transaction) {
        super("To and from users are the same in " + transaction);
        this.transaction = transaction;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
}

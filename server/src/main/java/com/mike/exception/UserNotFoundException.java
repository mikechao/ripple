package com.mike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mike.Transaction;
import com.mike.User;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7112332237603404913L;

    private final Transaction transaction;
    
    public UserNotFoundException(User user, Transaction transaction) {
        super(user + " is not found for " + transaction);
        this.transaction = transaction;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
}

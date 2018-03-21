package com.mike.exception;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
    private static final long serialVersionUID = 6991111940559473495L;
    private String message;

    public ErrorMessage() {
        
    }
    
    public ErrorMessage(String message) {
        this.message = message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}

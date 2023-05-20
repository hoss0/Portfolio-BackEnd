package com.portfolio.app.model.entities;


public class ErrorDetails {
    private String message;
    
    public ErrorDetails(String message) {
        this.message = message;
    }

    
    // Getter and setter
    
    public String getMessage() {
    	return message;
    }
    
    public void setMessage(String message) {
    	this.message = message;
    }
}
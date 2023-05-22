package com.hos.app.Entities;


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
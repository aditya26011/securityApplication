package com.coding.securityApp.securityApplication.exceptions;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String message){
        super(message);
    }
}

package com.ecommerce.exception;

public class ResourceNotExistsException extends RuntimeException{
    public ResourceNotExistsException(String message){
        super(message);
    }

}

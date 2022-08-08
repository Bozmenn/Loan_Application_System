package com.berkozmen.loan_application_system.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName) {
        super("Related " + entityName + " not found!");
    }
}

package com.zhartunmatthew.web.contactbook.services.exception;

public class ServiceException extends Exception {
    public ServiceException(String message, Exception exception) {
        super(message, exception);
    }

    public ServiceException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "ServiceException{} " + super.getMessage() + super.getCause();
    }
}

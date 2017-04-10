package com.zhartunmatthew.web.contactbook.dbmanager.exception;

public class ConnectionManagerException extends Exception{

    public ConnectionManagerException(String message, Exception exception) {
        super(message, exception);
    }

    public ConnectionManagerException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "ConnectionManagerException{} " + super.getMessage() + "\n" + super.getCause();
    }
}

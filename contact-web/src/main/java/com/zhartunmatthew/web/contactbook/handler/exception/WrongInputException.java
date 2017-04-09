package com.zhartunmatthew.web.contactbook.handler.exception;

public class WrongInputException extends Exception {
    public WrongInputException(String message, Exception exception) {
        super(message, exception);
    }

    public WrongInputException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "WrongInputException{} " + super.getMessage() + super.getCause();
    }
}

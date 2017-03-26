package com.zhartunmatthew.web.contactbook.dao.exception;

public class DAOException extends Exception{
    public DAOException(String message, Exception exception) {
        super(message, exception);
    }

    public DAOException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "DAOException{} " + super.getMessage() + super.getCause();
    }
}

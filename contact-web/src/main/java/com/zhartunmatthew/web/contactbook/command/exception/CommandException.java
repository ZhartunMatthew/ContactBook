package com.zhartunmatthew.web.contactbook.command.exception;

public class CommandException extends Exception{
    public CommandException(String message, Exception exception) {
        super(message, exception);
    }

    public CommandException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CommandException{} " + super.getMessage() + "\n" + super.getCause();
    }
}

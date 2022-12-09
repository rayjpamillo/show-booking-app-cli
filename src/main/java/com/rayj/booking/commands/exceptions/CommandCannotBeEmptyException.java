package com.rayj.booking.commands.exceptions;

public class CommandCannotBeEmptyException extends Exception {
    public CommandCannotBeEmptyException(String message) {
        super(message);
    }
}

package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class ShowAlreadyExistingException extends CommandRuntimeException {
    public ShowAlreadyExistingException(String message) {
        super(message);
    }
}

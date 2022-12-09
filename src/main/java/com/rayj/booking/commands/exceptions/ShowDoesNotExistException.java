package com.rayj.booking.commands.exceptions;

import com.rayj.booking.commands.exceptions.runtimeExceptions.CommandRuntimeException;

public class ShowDoesNotExistException extends CommandRuntimeException {
    public ShowDoesNotExistException(int showNumber) {
        super(String.format("Show %s does not exist from the list of shows.", showNumber));
    }
}

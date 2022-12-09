package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class CommandRuntimeException extends RuntimeException {
    public CommandRuntimeException(String message){
        super(message);
    }
}

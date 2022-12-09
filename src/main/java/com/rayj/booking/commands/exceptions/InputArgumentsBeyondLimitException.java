package com.rayj.booking.commands.exceptions;

public class InputArgumentsBeyondLimitException extends Exception {
    public InputArgumentsBeyondLimitException(String message) {
        super(message);
    }
}

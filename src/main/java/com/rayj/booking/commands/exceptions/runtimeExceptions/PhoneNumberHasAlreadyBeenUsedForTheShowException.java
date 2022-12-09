package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class PhoneNumberHasAlreadyBeenUsedForTheShowException extends CommandRuntimeException {
    public PhoneNumberHasAlreadyBeenUsedForTheShowException(String phoneNumber, int showNumber) {
        super(String.format("PhoneNumber %s Has already been used for the Show %s", phoneNumber, showNumber));
    }
}

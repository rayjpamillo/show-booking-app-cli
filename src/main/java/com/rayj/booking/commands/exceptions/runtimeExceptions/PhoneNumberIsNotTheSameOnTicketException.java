package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class PhoneNumberIsNotTheSameOnTicketException extends CommandRuntimeException {
    public PhoneNumberIsNotTheSameOnTicketException(String phoneNumber) {
        super(String.format("PhoneNumber %s provided is not the same with the registered phoneNumber on the ticket", phoneNumber));
    }
}

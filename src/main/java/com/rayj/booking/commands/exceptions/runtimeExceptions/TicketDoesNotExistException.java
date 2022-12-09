package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class TicketDoesNotExistException extends CommandRuntimeException {
    public TicketDoesNotExistException(String ticketNumber) {
        super(String.format("TicketNumber %s does not exist.", ticketNumber));
    }
}

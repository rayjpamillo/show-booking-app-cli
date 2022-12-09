package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class TicketBeyondCancellationPeriod extends CommandRuntimeException {
    public TicketBeyondCancellationPeriod(String ticketNumber, int showNumber, int cancellationPeriodInSeconds) {
        super(String.format("TicketNumber %s for ShowNumber %s is beyond the cancellation limit of %s minutes",
                ticketNumber, showNumber, cancellationPeriodInSeconds/60));
    }
}

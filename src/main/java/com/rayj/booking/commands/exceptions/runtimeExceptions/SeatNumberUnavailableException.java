package com.rayj.booking.commands.exceptions.runtimeExceptions;

public class SeatNumberUnavailableException extends CommandRuntimeException {
    public SeatNumberUnavailableException(String seatNo, String availableSeats) {
        super(String.format("SeatNo %s is not in the available list of seats. Available seats are :\r\n %s", seatNo, availableSeats));
    }
}

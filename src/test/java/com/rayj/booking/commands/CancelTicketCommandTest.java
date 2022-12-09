package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.PhoneNumberIsNotTheSameOnTicketException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.SeatNumberUnavailableException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.TicketBeyondCancellationPeriod;
import com.rayj.booking.commands.exceptions.runtimeExceptions.TicketDoesNotExistException;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CancelTicketCommandTest {

    CancelTicketCommand cancelTicketCommand;

    @BeforeEach
    void setUp() {
        cancelTicketCommand = new CancelTicketCommand();
    }

    @Test
    void assignArguments() throws Exception {
        String[] input = "cancel 1679434324 543674565".split(" ");
        cancelTicketCommand.assignArguments(input);

        assertEquals("1679434324", cancelTicketCommand.getTicketNumber());
        assertEquals("543674565", cancelTicketCommand.getPhoneNumber());
    }

    @Test
    void assignArgumentsThrowsExceptionWhenNotEnoughInputArguments() throws Exception {
        String[] input = "cancel 1679434324".split(" ");
        assertThrows(NotEnoughInputArgumentsException.class, () -> cancelTicketCommand.assignArguments(input));
    }


    @Test
    void executeProceedsSuccessfully() throws Exception {
        ShowsManager.getINSTANCE().addShow(6351, new Show(6351, 5, 5, 120));
        String ticketNumber = TicketsManager.getINSTANCE().createTicket(6351, "671249300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));

        String[] input = ("cancel " + ticketNumber + " 671249300").split(" ");
        cancelTicketCommand.assignArguments(input);

        cancelTicketCommand.execute();
    }

    @Test
    void executeProceedsSuccessfullyWithinTimeLimit() throws Exception {
        ShowsManager.getINSTANCE().addShow(6289, new Show(6289, 5, 5, 2));
        String ticketNumber = TicketsManager.getINSTANCE().createTicket(6289, "671249300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        String[] input = ("cancel " + ticketNumber + " 671249300").split(" ");
        cancelTicketCommand.assignArguments(input);

        cancelTicketCommand.execute();
    }

    @Test
    void executeThrowsExceptionWhenTicketDoesNotExist() throws Exception {
        String[] input = "cancel 3232134515 4345293750".split(" ");
        cancelTicketCommand.assignArguments(input);

        assertThrows(TicketDoesNotExistException.class, () -> cancelTicketCommand.execute());
    }

    @Test
    void executeThrowsExceptionWhenPhoneNumberProvidedIsNotTheSameOnTheTicket() throws Exception {
        ShowsManager.getINSTANCE().addShow(6357, new Show(6357, 5, 5, 120));
        String ticketNumber = TicketsManager.getINSTANCE().createTicket(6357, "612349300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));

        String[] input = ("cancel " + ticketNumber + " 642349300").split(" ");
        cancelTicketCommand.assignArguments(input);

        assertThrows(PhoneNumberIsNotTheSameOnTicketException.class, () -> cancelTicketCommand.execute());
    }

    @Test
    void executeThrowsExceptionWhenTicketIsBeyondCancellationPeriod() throws Exception {
        ShowsManager.getINSTANCE().addShow(6452, new Show(6452, 5, 5, 1));
        String ticketNumber = TicketsManager.getINSTANCE().createTicket(6452, "612349311", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        String[] input = ("cancel " + ticketNumber + " 612349311").split(" ");
        cancelTicketCommand.assignArguments(input);

        assertThrows(TicketBeyondCancellationPeriod.class, () -> cancelTicketCommand.execute());
    }
}
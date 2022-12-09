package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.PhoneNumberHasAlreadyBeenUsedForTheShowException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.SeatNumberUnavailableException;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class BookShowCommandTest {

    BookShowCommand bookShowCommand;

    @BeforeEach
    void setUp() {
        bookShowCommand = new BookShowCommand();
    }

    @Test
    void assignArguments() throws Exception {
        String[] input = "book 1391 2344323424 A1,A2,A3".split(" ");
        bookShowCommand.assignArguments(input);

        assertEquals(1391, bookShowCommand.getShowNumber());
        assertEquals("2344323424", bookShowCommand.getPhoneNumber());
        assertEquals(3, bookShowCommand.getSeatsToBook().size());
        assertTrue(bookShowCommand.getSeatsToBook().contains("A1"));
        assertTrue(bookShowCommand.getSeatsToBook().contains("A2"));
        assertTrue(bookShowCommand.getSeatsToBook().contains("A3"));

    }

    @Test
    void assignArgumentsWithSingleSeat() throws Exception {
        String[] input = "book 1391 2344323424 A1".split(" ");
        bookShowCommand.assignArguments(input);

        assertEquals(1391, bookShowCommand.getShowNumber());
        assertEquals("2344323424", bookShowCommand.getPhoneNumber());
        assertEquals(1, bookShowCommand.getSeatsToBook().size());
        assertTrue(bookShowCommand.getSeatsToBook().contains("A1"));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenNotEnoughInputArguments() throws Exception {
        String[] input = "book 1391 2344323424".split(" ");
        assertThrows(NotEnoughInputArgumentsException.class, () -> bookShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInvalidInputArguments() throws Exception {
        String[] input = "book 1abc 2344323424 A1,A2".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> bookShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenShowNumberInputIsNegative() throws Exception {
        String[] input = "book -5 2344323424 A1,A2".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> bookShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenShowNumberInputIsZero() throws Exception {
        String[] input = "book 0 2344323424 A1,A2".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> bookShowCommand.assignArguments(input));
    }

    @Test
    void executeProceedsSuccessfullyForShowWithBookings() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1551, new Show(1551, 5, 5,120));
        TicketsManager.getINSTANCE().createTicket(1551, "675849300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));
        TicketsManager.getINSTANCE().createTicket(1551, "344549300", new HashSet<>(Arrays.asList("E1,E2,E3,E4,E5".split(","))));

        String[] input = "book 1551 5437632150 d1,d2,d3,A1".split(" ");
        bookShowCommand.assignArguments(input);

        bookShowCommand.execute();
    }

    @Test
    void executeProceedsSuccessfullyForUnbookedShow() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1101, new Show(1101, 4,5, 120));
        String[] input = "book 1101 5437632150 d1,d2,d3".split(" ");
        bookShowCommand.assignArguments(input);

        bookShowCommand.execute();
    }

    @Test
    void executeThrowsExceptionWhenShowDoesNotExist() throws Exception  {
        String[] input = "book 1689 4345293750 c1,d1".split(" ");
        bookShowCommand.assignArguments(input);

        assertThrows(ShowDoesNotExistException.class, () -> bookShowCommand.execute());
    }

    @Test
    void executeThrowsExceptionWhenPhoneNumberWasUsedForTheShow() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1779, new Show(1779, 4,5, 120));
        TicketsManager.getINSTANCE().createTicket(1779, "4345293750",
                new HashSet<>(Arrays.asList("B2,B3".split(","))));
        String[] input = "book 1779 4345293750 C1,D1".split(" ");
        bookShowCommand.assignArguments(input);

        assertThrows(PhoneNumberHasAlreadyBeenUsedForTheShowException.class, () -> bookShowCommand.execute());
    }

    @Test
    void executeThrowsExceptionWhenSeatNumberIsUnavailable() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1889, new Show(1889, 4,5, 120));
        TicketsManager.getINSTANCE().createTicket(1889, "4345293750",
                new HashSet<>(Arrays.asList("B2,B3,B4".split(","))));
        String[] input = "book 1889 5437653750 B4,B5".split(" ");
        bookShowCommand.assignArguments(input);

        assertThrows(SeatNumberUnavailableException.class, () -> bookShowCommand.execute());
    }

    @Test
    void executeThrowsExceptionWhenSeatNumberIsOutOfBounds() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1098, new Show(1098, 4,5, 120));
        String[] input = "book 1098 5437653750 J3".split(" ");
        bookShowCommand.assignArguments(input);

        assertThrows(SeatNumberUnavailableException.class, () -> bookShowCommand.execute());
    }
}
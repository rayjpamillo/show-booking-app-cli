package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityCommandTest {

    AvailabilityCommand availabilityCommand;

    @BeforeEach
    void setUp() {
        availabilityCommand = new AvailabilityCommand();
    }

    @Test
    void assignArguments() throws Exception {
        String[] input = "availability 2121".split(" ");
        availabilityCommand.assignArguments(input);

        assertEquals(2121, availabilityCommand.getShowNumber());

    }

    @Test
    void assignArgumentsThrowsExceptionWhenNotEnoughInputArguments() throws Exception {
        String[] input = "availability".split(" ");
        assertThrows(NotEnoughInputArgumentsException.class, () -> availabilityCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInvalidInputArguments() throws Exception {
        String[] input = "availability 1abc".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> availabilityCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInputArgumentIsNegative() throws Exception {
        String[] input = "availability -5".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> availabilityCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInputArgumentIsZero() throws Exception {
        String[] input = "availability 0".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> availabilityCommand.assignArguments(input));
    }

    @Test
    void executeProceedsSuccessfullyForShowWithBookings() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1999, new Show(1999, 5, 5,120));
        TicketsManager.getINSTANCE().createTicket(1999, "675849300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));
        TicketsManager.getINSTANCE().createTicket(1999, "344549300", new HashSet<>(Arrays.asList("E1,E2,E3,E4,E5".split(","))));

        String[] input = "availability 1999".split(" ");
        availabilityCommand.assignArguments(input);

        availabilityCommand.execute();
    }

    @Test
    void executeProceedsSuccessfullyForUnbookedShow() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1769, new Show(1769, 3, 3,120));

        String[] input = "availability 1769".split(" ");
        availabilityCommand.assignArguments(input);

        availabilityCommand.execute();
    }

    @Test
    void executeThrowsExceptionWhenShowDoesNotExist() throws Exception  {
        String[] input = "availability 1690".split(" ");
        availabilityCommand.assignArguments(input);

        assertThrows(ShowDoesNotExistException.class, () -> availabilityCommand.execute());
    }
}
package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.RowLimitExceededException;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AddRowCommandTest {

    AddRowCommand addRowCommand;

    @BeforeEach
    void setUp() {
        addRowCommand = new AddRowCommand();
    }

    @Test
    void assignArguments() throws Exception {
        String[] input = "add 1679".split(" ");
        addRowCommand.assignArguments(input);

        assertEquals(1679, addRowCommand.getShowNumber());

    }

    @Test
    void assignArgumentsThrowsExceptionWhenNotEnoughInputArguments() throws Exception {
        String[] input = "add".split(" ");
        assertThrows(NotEnoughInputArgumentsException.class, () -> addRowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInvalidInputArguments() throws Exception {
        String[] input = "add 1abc".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> addRowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInputArgumentIsNegative() throws Exception {
        String[] input = "add -5".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> addRowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInputArgumentIsZero() throws Exception {
        String[] input = "add 0".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> addRowCommand.assignArguments(input));
    }

    @Test
    void executeProceedsSuccessfullyForShowWithBookings() throws Exception  {
        ShowsManager.getINSTANCE().addShow(3781, new Show(3781, 4, 10,120));
        TicketsManager.getINSTANCE().createTicket(3781, "675849300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));
        TicketsManager.getINSTANCE().createTicket(3781, "344549300", new HashSet<>(Arrays.asList("D1,D2,D3,D4,D5,D6,D7,D8,D9,D10".split(","))));

        String[] input = "view 3781".split(" ");
        addRowCommand.assignArguments(input);

        addRowCommand.execute();
    }

    @Test
    void executeProceedsSuccessfullyForUnbookedShow() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1663, new Show(1663, 3, 3,120));

        String[] input = "add 1663".split(" ");
        addRowCommand.assignArguments(input);

        addRowCommand.execute();
    }

    @Test
    void executeThrowsExceptionWhenRowLimitExceedsForShow() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1773, new Show(1773, 26, 7, 480));
        String[] input = "add 1773".split(" ");
        addRowCommand.assignArguments(input);

        assertThrows(RowLimitExceededException.class, () -> addRowCommand.execute());
    }

    @Test
    void executeThrowsExceptionWhenShowDoesNotExist() throws Exception  {
        String[] input = "add 1689".split(" ");
        addRowCommand.assignArguments(input);

        assertThrows(ShowDoesNotExistException.class, () -> addRowCommand.execute());
    }
}
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

class ViewShowCommandTest {

    ViewShowCommand viewShowCommand;

    @BeforeEach
    void setup(){
        viewShowCommand = new ViewShowCommand();
    }

    @Test
    void assignArguments() throws Exception {
        String[] input = "view 1679".split(" ");
        viewShowCommand.assignArguments(input);

        assertEquals(1679, viewShowCommand.getShowNumber());

    }

    @Test
    void assignArgumentsThrowsExceptionWhenNotEnoughInputArguments() throws Exception {
        String[] input = "view".split(" ");
        assertThrows(NotEnoughInputArgumentsException.class, () -> viewShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInvalidInputArguments() throws Exception {
        String[] input = "view 1abc".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> viewShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInputArgumentIsNegative() throws Exception {
        String[] input = "view -5".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> viewShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInputArgumentIsZero() throws Exception {
        String[] input = "view 0".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> viewShowCommand.assignArguments(input));
    }

    @Test
    void executeProceedsSuccessfullyForShowWithBookings() throws Exception  {
        ShowsManager.getINSTANCE().addShow(3321, new Show(3321, 5, 5,180));
        TicketsManager.getINSTANCE().createTicket(3321, "675849300", new HashSet<>(Arrays.asList("C1,C2,C3".split(","))));
        TicketsManager.getINSTANCE().createTicket(3321, "344549300", new HashSet<>(Arrays.asList("E1,E2,E3,E4,E5".split(","))));

        String[] input = "view 3321".split(" ");
        viewShowCommand.assignArguments(input);

        viewShowCommand.execute();
    }

    @Test
    void executeProceedsSuccessfullyForUnbookedShow() throws Exception  {
        ShowsManager.getINSTANCE().addShow(1441, new Show(1441, 3, 3,120));

        String[] input = "view 1441".split(" ");
        viewShowCommand.assignArguments(input);

        viewShowCommand.execute();
    }

    @Test
    void executeThrowsExceptionWhenShowDoesNotExist() throws Exception  {
        String[] input = "view 1689".split(" ");
        viewShowCommand.assignArguments(input);

        assertThrows(ShowDoesNotExistException.class, () -> viewShowCommand.execute());
    }
}
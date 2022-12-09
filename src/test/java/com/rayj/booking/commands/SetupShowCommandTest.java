package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InputArgumentsBeyondLimitException;
import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.ShowAlreadyExistingException;
import com.rayj.booking.models.Show;
import com.rayj.booking.managers.ShowsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetupShowCommandTest {

    SetupShowCommand setupShowCommand;

    @BeforeEach
    void setup() {
        setupShowCommand = new SetupShowCommand();
    }

    @Test
    void assignArguments() throws Exception {
        String[] input = "setup 1 2 3 4".split(" ");
        setupShowCommand.assignArguments(input);

        assertEquals(1, setupShowCommand.getShowNumber());
        assertEquals(2, setupShowCommand.getRows());
        assertEquals(3, setupShowCommand.getSeatsPerRow());
        assertEquals(4, setupShowCommand.getCancellationWindowInMinutes());
    }

    @Test
    void assignArgumentsThrowsExceptionWhenNotEnoughInputArguments() throws Exception {
        String[] input = "setup 1 2 3".split(" ");
        assertThrows(NotEnoughInputArgumentsException.class, () -> setupShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenInvalidInputArguments() throws Exception {
        String[] input = "setup 1 2 3a 5".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> setupShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenSomeInputArgumentsAreNegative() throws Exception {
        String[] input = "setup 1 2 -3 5".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> setupShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenSomeInputArgumentsAreZero() throws Exception {
        String[] input = "setup 1 2 3 0".split(" ");
        assertThrows(InvalidInputArgumentsException.class, () -> setupShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenRowInputIsBeyondLimit() throws Exception {
        String[] input = "setup 1 30 3 2".split(" ");
        assertThrows(InputArgumentsBeyondLimitException.class, () -> setupShowCommand.assignArguments(input));
    }

    @Test
    void assignArgumentsThrowsExceptionWhenSeatsPerRowInputIsBeyondLimit() throws Exception {
        String[] input = "setup 1 10 30 2".split(" ");
        assertThrows(InputArgumentsBeyondLimitException.class, () -> setupShowCommand.assignArguments(input));
    }

    @Test
    void executeAddsNewShowSuccessfully() throws Exception {
        String[] input = "setup 12345 3 5 2".split(" ");
        setupShowCommand.assignArguments(input);

        setupShowCommand.execute();

        ShowsManager showsManager = ShowsManager.getINSTANCE();
        assertNotNull(showsManager.getShow(12345));
    }

    @Test
    void executeShouldNotOverrideExistingShow() throws Exception {
        ShowsManager showsManager = ShowsManager.getINSTANCE();
        showsManager.addShow(12355, new Show(12355, 4, 4, 180));
        assertNotNull(showsManager.getShow(12355));
        String[] input = "setup 12355 3 5 2".split(" ");
        setupShowCommand.assignArguments(input);

        assertThrows(ShowAlreadyExistingException.class, () -> setupShowCommand.execute());
    }
}
package com.rayj.booking.commands.parser;

import com.rayj.booking.commands.*;
import com.rayj.booking.commands.exceptions.CommandCannotBeEmptyException;
import com.rayj.booking.commands.exceptions.InvalidCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    static CommandParser commandParser;

    @BeforeEach
    void setUp() {
        commandParser = CommandParser.getINSTANCE();
        assertNotNull(CommandParser.getINSTANCE());
    }

    @Test
    void parseSetupShowCommand() throws Exception {
        String input = "Setup 1 2 3 4";
        Command command = commandParser.parseCommand(input);
        assertInstanceOf(SetupShowCommand.class, command);
    }

    @Test
    void parseViewShowCommand() throws Exception {
        String input = "View 1 2 3";
        Command command = commandParser.parseCommand(input);
        assertInstanceOf(ViewShowCommand.class, command);
    }

    @Test
    void parseAddRowCommand() throws Exception {
        String input = "Add 1 2 3";
        Command command = commandParser.parseCommand(input);
        assertInstanceOf(AddRowCommand.class, command);
    }

    @Test
    void parseAvailabilityCommand() throws Exception {
        String input = "Availability 1 2 3";
        Command command = commandParser.parseCommand(input);
        assertInstanceOf(AvailabilityCommand.class, command);
    }

    @Test
    void parseBookShowCommand() throws Exception {
        String input = "Book 1 2 3";
        Command command = commandParser.parseCommand(input);
        assertInstanceOf(BookShowCommand.class, command);
    }


    @Test
    void parseCancelTicketCommand() throws Exception {
        String input = "Cancel 1 2 3";
        Command command = commandParser.parseCommand(input);
        assertInstanceOf(CancelTicketCommand.class, command);
    }

    @Test
    void parseCommandShouldNotHaveEmptyInput() {
        String input = "";
        assertThrows(CommandCannotBeEmptyException.class, () -> commandParser.parseCommand(input));
    }

    @Test
    void parseCommandShouldNotHaveInvalidCommandInput() {
        String input = "anystring 1 2 3";
        assertThrows(InvalidCommandException.class, () -> commandParser.parseCommand(input));
    }
}
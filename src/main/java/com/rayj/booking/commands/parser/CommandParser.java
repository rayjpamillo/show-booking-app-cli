package com.rayj.booking.commands.parser;

import com.rayj.booking.commands.*;
import com.rayj.booking.commands.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CommandParser {

    private static final Logger LOG = LoggerFactory
            .getLogger(CommandParser.class);

    private static final String EMPTY_STRING = "";
    private static final String WHITESPACE = " ";

    private static CommandParser INSTANCE;
    private CommandParser() {
    }

    public static CommandParser getINSTANCE(){
        if (INSTANCE == null) {
            INSTANCE = new CommandParser();
        }
        return INSTANCE;
    }

    public Command parseCommand(String commandInput) throws CommandCannotBeEmptyException, InvalidCommandException, NotEnoughInputArgumentsException, InvalidInputArgumentsException, InputArgumentsBeyondLimitException {
        String trimmedCommand = commandInput.trim();
        if(EMPTY_STRING.equals(trimmedCommand)){
            throw new CommandCannotBeEmptyException("Command input cannot be empty!");
        }
        String[] commandArguments = trimmedCommand.split(WHITESPACE);
        LOG.debug(Arrays.toString(commandArguments));

        Command command;

        switch (commandArguments[0].toLowerCase()){
            case "setup":
                command = new SetupShowCommand();
                break;
            case "view":
                command = new ViewShowCommand();
                break;
            case "add":
                command = new AddRowCommand();
                break;
            case "availability":
                command = new AvailabilityCommand();
                break;
            case "book":
                command = new BookShowCommand();
                break;
            case "cancel":
                command = new CancelTicketCommand();
                break;
            default:
                throw new InvalidCommandException("Invalid command input : " + commandArguments[0]);
        }
        command.assignArguments(commandArguments);
        return command;
    }
}

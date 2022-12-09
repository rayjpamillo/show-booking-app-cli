package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.managers.SeatsManager;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import com.rayj.booking.models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AvailabilityCommand implements Command {

    private int showNumber;
    private static final String TEMPLATE = "AVAILABILITY  <int showNumber>";
    private static final Logger LOG = LoggerFactory
            .getLogger(AvailabilityCommand.class);

    @Override
    public void assignArguments(String[] args) throws NotEnoughInputArgumentsException, InvalidInputArgumentsException {
        if (args.length < 2) {
            throw new NotEnoughInputArgumentsException(""
                    + "Not enough arguments for AVAILABILITY Command, refer to the template below and their data types: \r\n"
                    + TEMPLATE);
        }
        try {
            this.showNumber = Integer.parseInt(args[1]);

            verifyIfShowNumberIsPositive();
        } catch (NumberFormatException e) {
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for AVAILABILITY Command, refer to the template below and their data types: \r\n" +
                    TEMPLATE);
        }
    }

    private void verifyIfShowNumberIsPositive() throws InvalidInputArgumentsException {
        if(this.showNumber <= 0){
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for AVAILABILITY Command, showNumber should be positive integer.");
        }
    }

    @Override
    public void execute() {
        LOG.debug("Executing AVAILABILITY command...");

        SeatsManager seatsManager = SeatsManager.getINSTANCE();
        ShowsManager showsManager = ShowsManager.getINSTANCE();

        Show show = showsManager.getShow(showNumber);

        if(show == null){
            throw new ShowDoesNotExistException(this.showNumber);
        }

        List<String> availableSeats = seatsManager.getListOfAvailableSeatsSorted(showNumber);
        System.out.println(availableSeats.toString());
    }

    public int getShowNumber() {
        return showNumber;
    }
}

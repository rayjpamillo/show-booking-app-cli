package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import com.rayj.booking.models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ViewShowCommand implements Command {

    private int showNumber;

    private static final String TEMPLATE = "VIEW  <int showNumber>";


    private static final Logger LOG = LoggerFactory
            .getLogger(ViewShowCommand.class);

    @Override
    public void assignArguments(String[] args) throws NotEnoughInputArgumentsException, InvalidInputArgumentsException {
        if (args.length < 2) {
            throw new NotEnoughInputArgumentsException(""
                    + "Not enough arguments for VIEW Command, refer to the template below and their data types: \r\n"
                    + TEMPLATE);
        }
        try {
            this.showNumber = Integer.parseInt(args[1]);

            verifyIfShowNumberIsPositive();
        } catch (NumberFormatException e) {
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for VIEW Command, refer to the template below and their data types: \r\n" +
                    TEMPLATE);
        }
    }

    private void verifyIfShowNumberIsPositive() throws InvalidInputArgumentsException {
        if(this.showNumber <= 0){
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for VIEW Command, showNumber should be positive integer.");
        }
    }

    @Override
    public void execute() {
        LOG.debug("Executing VIEW command...");

        Show show = ShowsManager.getINSTANCE().getShow(showNumber);
        if(show == null){
            throw new ShowDoesNotExistException(this.showNumber);
        }

        TicketsManager ticketsManager = TicketsManager.getINSTANCE();
        List<Ticket> tickets = ticketsManager.getTicketsByShow(showNumber);
        for(Ticket ticket: tickets){
            System.out.println(ticket.toString());
        }
    }

    public int getShowNumber() {
        return showNumber;
    }
}

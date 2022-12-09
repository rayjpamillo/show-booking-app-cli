package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.PhoneNumberIsNotTheSameOnTicketException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.TicketBeyondCancellationPeriod;
import com.rayj.booking.commands.exceptions.runtimeExceptions.TicketDoesNotExistException;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import com.rayj.booking.models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class CancelTicketCommand implements Command{

    private String ticketNumber;
    private String phoneNumber;

    private static final String TEMPLATE = "CANCEL <String ticketNumber> <String phoneNumber>";

    private static final Logger LOG = LoggerFactory
            .getLogger(CancelTicketCommand.class);

    @Override
    public void assignArguments(String[] args) throws NotEnoughInputArgumentsException {
        if (args.length < 3) {
            throw new NotEnoughInputArgumentsException(""
                    + "Not enough arguments for CANCEL Command, refer to the template below and their data types: \r\n"
                    + TEMPLATE);
        }
        this.ticketNumber = args[1];
        this.phoneNumber = args[2];
    }

    @Override
    public void execute() {
        LOG.debug("Executing CANCEL command...");
        TicketsManager ticketsManager = TicketsManager.getINSTANCE();
        Ticket ticket = ticketsManager.getTicket(ticketNumber);
        if(ticket == null){
            throw new TicketDoesNotExistException(ticketNumber);
        } else if(!ticket.getPhoneNumber().equals(phoneNumber)){
            throw new PhoneNumberIsNotTheSameOnTicketException(phoneNumber);
        }

        ShowsManager showsManager = ShowsManager.getINSTANCE();
        int showNumber = ticket.getShowNumber();
        Show show = showsManager.getShow(showNumber);
        int cancellationPeriodInSeconds = show.getCancellationWindowInSeconds();

        if(isTicketBeyondCancellationPeriod(ticket, cancellationPeriodInSeconds)){
            throw new TicketBeyondCancellationPeriod(ticketNumber, showNumber, cancellationPeriodInSeconds);
        }

        ticketsManager.cancelTicket(ticketNumber);
        System.out.println(String.format("TicketNumber %s has been successfully cancelled.", ticketNumber));
    }

    private boolean isTicketBeyondCancellationPeriod(Ticket ticket, int cancellationPeriodInMinutes) {
        LocalDateTime ticketTimeStamp = ticket.getCreateTimestamp();
        return LocalDateTime.now().isAfter(ticketTimeStamp.plusSeconds(cancellationPeriodInMinutes));
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

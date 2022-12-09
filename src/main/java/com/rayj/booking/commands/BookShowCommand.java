package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.PhoneNumberHasAlreadyBeenUsedForTheShowException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.SeatNumberUnavailableException;
import com.rayj.booking.managers.SeatsManager;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.managers.TicketsManager;
import com.rayj.booking.models.Show;
import com.rayj.booking.models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class BookShowCommand implements Command{

    private int showNumber;
    private String phoneNumber;
    private List<String> seatsToBook;

    private static final String TEMPLATE = "BOOK  <int showNumber> <String phoneNumber> <String commaSeparatedSeats>";

    private static final Logger LOG = LoggerFactory
            .getLogger(BookShowCommand.class);

    @Override
    public void assignArguments(String[] args) throws NotEnoughInputArgumentsException, InvalidInputArgumentsException {
        if (args.length < 4) {
            throw new NotEnoughInputArgumentsException(""
                    + "Not enough arguments for BOOK Command, refer to the template below and their data types: \r\n"
                    + TEMPLATE);
        }
        try {
            this.showNumber = Integer.parseInt(args[1]);
            this.phoneNumber = args[2];
            this.seatsToBook = Arrays.stream(args[3].split(","))
                    .map(String::toUpperCase).collect(Collectors.toList());

            verifyIfShowNumberIsPositive();
        } catch (NumberFormatException e) {
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for BOOK Command, refer to the template below and their data types: \r\n" +
                    TEMPLATE);
        }
    }

    private void verifyIfShowNumberIsPositive() throws InvalidInputArgumentsException {
        if(this.showNumber <= 0){
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for BOOK Command, showNumber should be positive integer.");
        }
    }

    @Override
    public void execute() {
        LOG.debug("Executing BOOK command...");

        Show show = ShowsManager.getINSTANCE().getShow(showNumber);
        if(show == null){
            throw new ShowDoesNotExistException(this.showNumber);
        }

        TicketsManager ticketsManager = TicketsManager.getINSTANCE();
        List<Ticket> bookedTickets = ticketsManager.getTicketsByShow(showNumber);
        if(bookedTickets.stream().anyMatch(ticket -> ticket.getPhoneNumber().equals(phoneNumber))) {
            throw new PhoneNumberHasAlreadyBeenUsedForTheShowException(phoneNumber, showNumber);
        }

        List<String> availableSeats = SeatsManager.getINSTANCE().getListOfAvailableSeatsSorted(showNumber);
        for(String seatNo: seatsToBook){
            if(!availableSeats.contains(seatNo)){
                throw new SeatNumberUnavailableException(seatNo, availableSeats.toString());
            }
        }

        String ticketNumber = ticketsManager.createTicket(showNumber, phoneNumber, new HashSet<>(seatsToBook));
        System.out.println(String.format("Ticket Number %s created.", ticketNumber));
    }

    public int getShowNumber() {
        return showNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<String> getSeatsToBook() {
        return seatsToBook;
    }
}

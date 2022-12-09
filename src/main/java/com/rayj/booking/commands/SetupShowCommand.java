package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.*;
import com.rayj.booking.commands.exceptions.runtimeExceptions.ShowAlreadyExistingException;
import com.rayj.booking.constants.Constants;
import com.rayj.booking.models.Show;
import com.rayj.booking.managers.ShowsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetupShowCommand implements Command {

    private int showNumber;
    private int rows;
    private int seatsPerRow;
    private int cancellationWindowInMinutes;

    private static final String TEMPLATE = "SETUP  <int showNumber> <int rows> <int seatsPerRow>  <int cancellationWindowInMinutes>";

    private static final Logger LOG = LoggerFactory
            .getLogger(SetupShowCommand.class);

    @Override
    public void assignArguments(String[] args) throws NotEnoughInputArgumentsException, InvalidInputArgumentsException, InputArgumentsBeyondLimitException {
        if (args.length < 5) {
            throw new NotEnoughInputArgumentsException(""
                    + "Not enough arguments for SETUP Command, refer to the template below and their data types: \r\n"
                    + TEMPLATE);
        }
        try {
            this.showNumber = Integer.parseInt(args[1]);
            this.rows = Integer.parseInt(args[2]);
            this.seatsPerRow = Integer.parseInt(args[3]);
            this.cancellationWindowInMinutes = Integer.parseInt(args[4]);

            verifyIfNumbersArePositive();
            verifyIfLimitsAreReached();
        } catch (NumberFormatException e) {
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for SETUP Command, refer to the template below and their data types: \r\n" +
                    TEMPLATE);
        }
    }

    private void verifyIfLimitsAreReached() throws InputArgumentsBeyondLimitException {
        if(this.rows > Constants.MAX_ROW_LIMIT){
            throw new InputArgumentsBeyondLimitException("" +
                    "MAX_ROW_LIMIT is set to " + Constants.MAX_ROW_LIMIT);
        }

        if(this.seatsPerRow > Constants.MAX_SEAT_PER_ROW_LIMIT){
            throw new InputArgumentsBeyondLimitException("" +
                    "MAX_SEAT_PER_ROW_LIMIT is set to " + Constants.MAX_SEAT_PER_ROW_LIMIT);
        }
    }

    private void verifyIfNumbersArePositive() throws InvalidInputArgumentsException {
        if(!isPositive(this.showNumber)
                || !isPositive(this.rows)
                || !isPositive(this.seatsPerRow)
                || !isPositive(this.cancellationWindowInMinutes)){
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for SETUP Command, the following variables should be positive integer: \r\n" +
                    "showNumber, rows, seatsPerRow, cancellationWindowInMinutes");
        }
    }

    private boolean isPositive(int number) {
        return number > 0;
    }

    @Override
    public void execute() {
        LOG.debug("Executing SETUP command...");
        ShowsManager showsManager = ShowsManager.getINSTANCE();
        if(showsManager.getShow(this.showNumber) != null){
            throw new ShowAlreadyExistingException("Show already exists with this number: " + this.showNumber);
        }
        showsManager.addShow(this.showNumber, new Show(this.showNumber, this.rows, this.seatsPerRow, this.cancellationWindowInMinutes*60));
        System.out.printf("Show %s added to the list of shows.\r\n", this.showNumber);
    }

    public int getShowNumber() {
        return showNumber;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getCancellationWindowInMinutes() {
        return cancellationWindowInMinutes;
    }
}

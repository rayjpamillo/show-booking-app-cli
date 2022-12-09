package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;
import com.rayj.booking.commands.exceptions.ShowDoesNotExistException;
import com.rayj.booking.commands.exceptions.runtimeExceptions.RowLimitExceededException;
import com.rayj.booking.constants.Constants;
import com.rayj.booking.managers.ShowsManager;
import com.rayj.booking.models.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddRowCommand implements Command{

    private int showNumber;

    private static final String TEMPLATE = "ADD  <int showNumber>";

    private static final Logger LOG = LoggerFactory
            .getLogger(AddRowCommand.class);

    @Override
    public void assignArguments(String[] args) throws NotEnoughInputArgumentsException, InvalidInputArgumentsException {
        if (args.length < 2) {
            throw new NotEnoughInputArgumentsException(""
                    + "Not enough arguments for ADD Command, refer to the template below and their data types: \r\n"
                    + TEMPLATE);
        }
        try {
            this.showNumber = Integer.parseInt(args[1]);

            verifyIfShowNumberIsPositive();
        } catch (NumberFormatException e) {
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for ADD Command, refer to the template below and their data types: \r\n" +
                    TEMPLATE);
        }
    }

    private void verifyIfShowNumberIsPositive() throws InvalidInputArgumentsException {
        if(this.showNumber <= 0){
            throw new InvalidInputArgumentsException("" +
                    "Invalid arguments for ADD Command, showNumber should be positive integer.");
        }
    }

    @Override
    public void execute() {
        LOG.debug("Executing ADD command...");
        ShowsManager showsManager = ShowsManager.getINSTANCE();
        Show show = showsManager.getShow(showNumber);
        if(show == null){
            throw new ShowDoesNotExistException(this.showNumber);
        }
        if(show.getRows() + 1 > Constants.MAX_ROW_LIMIT){
            throw new RowLimitExceededException(showNumber);
        }
        showsManager.addRow(showNumber);
        System.out.println("One row of seats added to showNumber " + showNumber);
    }

    public int getShowNumber() {
        return showNumber;
    }
}

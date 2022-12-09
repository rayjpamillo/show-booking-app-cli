package com.rayj.booking.commands.exceptions.runtimeExceptions;

import com.rayj.booking.constants.Constants;

public class RowLimitExceededException extends CommandRuntimeException {
    public RowLimitExceededException(int showNumber) {
        super(String.format("Maximum row limit of %s exceeded for showNumber %s", Constants.MAX_ROW_LIMIT, showNumber));
    }
}

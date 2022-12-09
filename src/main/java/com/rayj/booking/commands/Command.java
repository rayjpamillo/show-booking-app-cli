package com.rayj.booking.commands;

import com.rayj.booking.commands.exceptions.InputArgumentsBeyondLimitException;
import com.rayj.booking.commands.exceptions.InvalidInputArgumentsException;
import com.rayj.booking.commands.exceptions.NotEnoughInputArgumentsException;

public interface Command {

    void assignArguments(String[] args) throws NotEnoughInputArgumentsException, InvalidInputArgumentsException, InputArgumentsBeyondLimitException;

    void execute();
}

package com.rayj.booking;

import com.rayj.booking.commands.Command;
import com.rayj.booking.commands.exceptions.*;
import com.rayj.booking.commands.exceptions.runtimeExceptions.CommandRuntimeException;
import com.rayj.booking.commands.parser.CommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Console;

@SpringBootApplication
public class BookingApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory
			.getLogger(BookingApplication.class);
	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(BookingApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");
		boolean isRunning=true;

		do {
			Console console = System.console();
			if (console != null) {
				System.out.println("----------------------------------------------------------------------------------------------");
				System.out.println("Console is ready to accept new command input...");
				String commandInput = console.readLine();
				LOG.debug(commandInput);
				executeCommandInput(commandInput);
			} else {
				isRunning = false;
			}
		} while (isRunning);
	}

	private void executeCommandInput(String commandInput) {
		try{
			CommandParser commandParser = CommandParser.getINSTANCE();
			Command command = commandParser.parseCommand(commandInput);
			System.out.println("Executing command...");
			command.execute();
		} catch (InvalidCommandException | CommandCannotBeEmptyException | NotEnoughInputArgumentsException |
				 InvalidInputArgumentsException | InputArgumentsBeyondLimitException | CommandRuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
}

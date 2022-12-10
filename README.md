# show-booking-app-cli

This is a cli application for managing and booking shows with rectangular grid sitting arrangement. It is written in java and can be run using the mvn spring-boot runner.

## Commands
Commands can be issued in uppercase or lowercase.

`SETUP` - setups the show with its showNumber, sitting arrangement and cancellation period upon booking
```
setup <showNumber> <rows> <seatsPerRow> <cancellationPeriodInMinutes>
```

`VIEW` - views the ticket details that have been purchased for a particular show
```
view <showNumber>
```

`ADD` - adds an extra row of seats on a show
```
add <showNumber>
```

`AVAILABILITY` - displays the available seats on the show
```
availability <showNumber>
```

`BOOK` - books a ticket for a client on the show with their phoneNumber and desired seats
```
book <showNumber> <phoneNumber> <commaSeparatedSeatTags>
```

`CANCEL` - cancels a booked ticket
```
cancel <ticketId>
```

## Constraints
For SETUP command, a maximum of 26 rows and 10 rows per seat can be created.

For CANCEL command, a ticket cannot be cancelled beyond the cancellation period for the show.



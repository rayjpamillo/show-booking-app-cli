# show-booking-app-cli

This is a cli application for managing and booking shows with rectangular grid sitting arrangement. It is written in java and can be run using the mvn spring-boot runner.

## Commands
Commands can be issued in uppercase or lowercase.

### SETUP
Setups the show with its showNumber, sitting arrangement and cancellation period upon booking.
```
setup <showNumber> <rows> <seatsPerRow> <cancellationPeriodInMinutes>
```
Only a maximum of 26 rows and 10 seatsPerRow can be set.

### VIEW
Views the ticket details that have been purchased for a particular show
```
view <showNumber>
```

### ADD 
Adds an extra row of seats on a show
```
add <showNumber>
```
The user can add up to the maximum limit of 26 rows for a show.

### AVAILABILITY`
Displays the available seats on the show
```
availability <showNumber>
```

### BOOK
Books a ticket for a client on the show with their phoneNumber and desired seats
```
book <showNumber> <phoneNumber> <commaSeparatedSeatTags>
```

### CANCEL
Cancels a booked ticket within the cancellation period for the show.
```
cancel <ticketId> <phoneNumber>
```



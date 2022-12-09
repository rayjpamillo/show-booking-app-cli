package com.rayj.booking.models;

import java.time.LocalDateTime;
import java.util.Set;

public class Ticket {
    private final String ticketNumber;
    private final int showNumber;
    private final String phoneNumber;
    private final Set<String> seats;
    private final LocalDateTime createTimestamp;

    public Ticket(String ticketNumber, int showNumber, String phoneNumber, Set<String> seats) {
        this.ticketNumber = ticketNumber;
        this.showNumber = showNumber;
        this.phoneNumber = phoneNumber;
        this.seats = seats;
        this.createTimestamp = LocalDateTime.now();
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public int getShowNumber() {
        return showNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<String> getSeats() {
        return seats;
    }

    public LocalDateTime getCreateTimestamp(){
        return createTimestamp;
    }

    public String toString(){
        return String.format("showNumber: %s, ticketNumber: %s, phoneNumber: %s, createTimestamp: %s, seats: %s",
                showNumber, ticketNumber, phoneNumber, createTimestamp.toString(), seats.toString());
    }
}

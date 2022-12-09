package com.rayj.booking.managers.models;

import com.rayj.booking.models.Ticket;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void testToString() {
        String[] seatNos = "F3,F4,F5,F6,G4,G5".split(",");
        Set<String> seats = new HashSet<>(Arrays.asList(seatNos));
        Ticket ticket = new Ticket("f1349997", 4235, "9894432534", seats);

        assertEquals("showNumber: 4235, ticketNumber: f1349997, phoneNumber: 9894432534, createTimestamp: " +
                ticket.getCreateTimestamp().toString() +
                ", seats: [F6, F3, G4, F4, G5, F5]", ticket.toString());
    }
}
package com.rayj.booking.managers;

import com.rayj.booking.models.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TicketsManagerTest {

    TicketsManager ticketsManager;


    @BeforeEach
    void setUp() {
        ticketsManager = TicketsManager.getINSTANCE();
    }

    @Test
    void getINSTANCE() {
        assertNotNull(ticketsManager);
    }

    @Test
    void createTicket() {
        Set<String> seats = new HashSet<>();
        seats.add("A3");
        seats.add("A4");

        String ticketNumber = ticketsManager.createTicket(1345, "5567883458", seats);

        assertNotNull(ticketsManager.getTicket(ticketNumber));
    }

    @Test
    void getTicket() {
        Set<String> seats = new HashSet<>();
        seats.add("B7");
        seats.add("C5");
        seats.add("D2");

        String ticketNumber = ticketsManager.createTicket(1675, "5567823458", seats);
        Ticket ticket = ticketsManager.getTicket(ticketNumber);

        assertEquals(ticketNumber, ticket.getTicketNumber());
        assertEquals(1675, ticket.getShowNumber());
        assertEquals("5567823458", ticket.getPhoneNumber());
        assertEquals(3, ticket.getSeats().size());
        assertTrue(ticket.getSeats().contains("B7"));
        assertTrue(ticket.getSeats().contains("C5"));
        assertTrue(ticket.getSeats().contains("D2"));
    }

    @Test
    void getTicketsByShow() {
        ticketsManager.createTicket(1378, "5567822258", Collections.singleton("A3"));
        ticketsManager.createTicket(1388, "4317822258", Collections.singleton("A5"));
        ticketsManager.createTicket(1378, "5123822258", Collections.singleton("A7"));
        ticketsManager.createTicket(1378, "4317822258", Collections.singleton("G1"));
        ticketsManager.createTicket(1388, "5555666558", Collections.singleton("C3"));
        ticketsManager.createTicket(2148, "8955666558", Collections.singleton("C3"));

        List<Ticket> tickets = ticketsManager.getTicketsByShow(1378);

        assertEquals(3, tickets.size());

        List<Ticket> tickets2 = ticketsManager.getTicketsByShow(1388);

        assertEquals(2, tickets2.size());

        List<Ticket> tickets3 = ticketsManager.getTicketsByShow(2148);

        assertEquals(1, tickets3.size());

    }

    @Test
    void cancelTicket(){
        String ticketNumber = ticketsManager.createTicket(2145, "8955366558", Collections.singleton("C3"));

        assertNotNull(ticketsManager.getTicket(ticketNumber));

        ticketsManager.cancelTicket(ticketNumber);

        assertNull(ticketsManager.getTicket(ticketNumber));
    }
}
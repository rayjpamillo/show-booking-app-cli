package com.rayj.booking.managers;

import com.rayj.booking.models.Ticket;

import java.util.*;
import java.util.stream.Collectors;

public class TicketsManager {
    private static TicketsManager INSTANCE;

    private Map<String, Ticket> ticketsMap;

    private TicketsManager(){
        this.ticketsMap = new HashMap<>();
    }

    public static TicketsManager getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new TicketsManager();
        }
        return INSTANCE;
    }

    public String createTicket(int showNumber, String phoneNumber, Set<String> seats){
        String ticketNumber = UUID.randomUUID().toString().split("-")[0];
        ticketsMap.put(ticketNumber, new Ticket(ticketNumber, showNumber, phoneNumber, seats));
        return ticketNumber;
    }

    public Ticket getTicket(String ticketNumber){
        return ticketsMap.get(ticketNumber);
    }

    public List<Ticket> getTicketsByShow(int showNumber){
        return ticketsMap.values().stream()
                .filter(ticket -> showNumber == ticket.getShowNumber())
                .collect(Collectors.toList());
    }

    public void cancelTicket(String ticketNumber) {
        ticketsMap.remove(ticketNumber);
    }
}

package com.rayj.booking.managers;

import com.rayj.booking.models.Ticket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeatsManager {
    private static SeatsManager INSTANCE;

    private SeatsManager(){

    }

    public static SeatsManager getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new SeatsManager();
        }
        return INSTANCE;
    }

    public List<String> getListOfAvailableSeatsSorted(int showNumber){
        List<String> allSeats = ShowsManager.getINSTANCE().getShow(showNumber).getListOfAllSeatsSorted();
        List<Ticket> bookedTickets = TicketsManager.getINSTANCE().getTicketsByShow(showNumber);
        if(bookedTickets.size() == 0){
            return allSeats;
        }

        Set<String> seatsCopy = new HashSet<>(allSeats) ;
        Set<String> takenSeats = new HashSet<>();
        for(Ticket ticket: bookedTickets){
            takenSeats.addAll(ticket.getSeats());
        }
        seatsCopy.removeAll(takenSeats);
        return seatsCopy.stream().sorted().collect(Collectors.toList());
    }
}

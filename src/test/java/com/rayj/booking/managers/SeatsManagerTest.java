package com.rayj.booking.managers;

import com.rayj.booking.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeatsManagerTest {

    SeatsManager seatsManager;

    @BeforeEach
    void setUp() {
        seatsManager = SeatsManager.getINSTANCE();
    }

    @Test
    void getINSTANCE() {
        assertNotNull(seatsManager);
    }

    @Test
    void getListOfAvailableSeatsSortedForAnUnbookedShow() {
        // create show with labels A1 to E5, but with no bookings yet
        ShowsManager.getINSTANCE().addShow(9561, new Show(9561, 5, 5, 120));

        List<String> availableSeats = seatsManager.getListOfAvailableSeatsSorted(9561);

        assertEquals("[A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, C1, C2, C3, C4, C5, D1, D2, D3, D4, D5, E1, E2, E3, E4, E5]",
                availableSeats.toString());
    }

    @Test
    void getListOfAvailableSeatsSortedForAShowWithOneBooking() {
        // create show with labels A1 to E5
        ShowsManager.getINSTANCE().addShow(9770, new Show(9770, 5, 5, 120));
        TicketsManager.getINSTANCE().createTicket(9770, "4345683646",
                new HashSet<>(Arrays.asList("A3,A4,B3,B4,C3,C4".split(","))));

        List<String> availableSeats = seatsManager.getListOfAvailableSeatsSorted(9770);

        assertEquals("[A1, A2, A5, B1, B2, B5, C1, C2, C5, D1, D2, D3, D4, D5, E1, E2, E3, E4, E5]",
                availableSeats.toString());
    }

    @Test
    void getListOfAvailableSeatsSortedForAShowWithMultipleBookings() {
        // create show with labels A1 to F6
        ShowsManager.getINSTANCE().addShow(9301, new Show(9301, 6, 6, 120));
        TicketsManager.getINSTANCE().createTicket(9301, "4343322116",
                new HashSet<>(Arrays.asList("A3,A4,B3,B4,C3,C4".split(","))));
        TicketsManager.getINSTANCE().createTicket(9301, "4333211007",
                new HashSet<>(Arrays.asList("A1,A2,B1,B2".split(","))));
        TicketsManager.getINSTANCE().createTicket(9301, "4532140079",
                new HashSet<>(Arrays.asList("A5,A6,B5,B6,C5,C6,D4,D5,D6,E4,E5,E6".split(","))));
        TicketsManager.getINSTANCE().createTicket(9301, "3232140079",
                Collections.singleton("F6"));

        List<String> availableSeats = seatsManager.getListOfAvailableSeatsSorted(9301);

        assertEquals("[C1, C2, D1, D2, D3, E1, E2, E3, F1, F2, F3, F4, F5]",
                availableSeats.toString());
    }

}
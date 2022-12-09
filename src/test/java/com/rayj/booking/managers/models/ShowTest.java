package com.rayj.booking.managers.models;

import com.rayj.booking.models.Show;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {

    @Test
    void getShowNumber() {
        Show show = new Show(1234, 26, 1, 120);

        assertEquals(1234, show.getShowNumber());
    }

    @Test
    void getRows() {
        Show show = new Show(1234, 26, 1, 120);

        assertEquals(26, show.getRows());
    }

    @Test
    void getSeatsPerRow() {
        Show show = new Show(1234, 26, 3, 120);

        assertEquals(3, show.getSeatsPerRow());
    }

    @Test
    void getCancellationWindowInSeconds() {
        Show show = new Show(1234, 26, 3, 120);

        assertEquals(2, show.getCancellationWindowInSeconds());
    }

    @Test
    void getCancellationWindowInMinutes() {
        Show show = new Show(1234, 26, 3, 120);

        assertEquals(2, show.getCancellationWindowInSeconds());
    }

    @Test
    void getListOfAllSeatsForMaxRowsAndOneSeatPerRow() {
        Show show = new Show(1234, 26, 1, 120);

        assertEquals("[A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1, Y1, Z1]"
                , show.getListOfAllSeatsSorted().toString());
    }

    @Test
    void getListOfAllSeatsFor6RowsAnd10SeatsPerRow() {
        Show show = new Show(1234, 6, 10, 120);

        assertEquals("[A1, A10, A2, A3, A4, A5, A6, A7, A8, A9, " +
                        "B1, B10, B2, B3, B4, B5, B6, B7, B8, B9, " +
                        "C1, C10, C2, C3, C4, C5, C6, C7, C8, C9, " +
                        "D1, D10, D2, D3, D4, D5, D6, D7, D8, D9, " +
                        "E1, E10, E2, E3, E4, E5, E6, E7, E8, E9, " +
                        "F1, F10, F2, F3, F4, F5, F6, F7, F8, F9]"
                , show.getListOfAllSeatsSorted().toString());
    }

    @Test
    void addRow(){
        Show show = new Show(1234, 4, 5, 120);
        assertEquals(4, show.getRows());
        assertEquals("[A1, A2, A3, A4, A5, " +
                        "B1, B2, B3, B4, B5, " +
                        "C1, C2, C3, C4, C5, " +
                        "D1, D2, D3, D4, D5]"
                , show.getListOfAllSeatsSorted().toString());

        show.addRow();
        assertEquals(5, show.getRows());
        assertEquals("[A1, A2, A3, A4, A5, " +
                        "B1, B2, B3, B4, B5, " +
                        "C1, C2, C3, C4, C5, " +
                        "D1, D2, D3, D4, D5, " +
                        "E1, E2, E3, E4, E5]"
                , show.getListOfAllSeatsSorted().toString());
    }
}
package com.rayj.booking.models;

import java.util.*;
import java.util.stream.Collectors;

import static com.rayj.booking.constants.Constants.LETTERS;


public class Show {
    private final int showNumber;
    private int rows; // max 26
    private final int seatsPerRow; // max 10
    private final int cancellationWindowInSeconds;

    private Set<String> seats;


    public Show(int showNumber, int rows, int seatsPerRow, int cancellationWindowInSeconds) {
        this.showNumber = showNumber;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindowInSeconds = cancellationWindowInSeconds;
        this.seats = new HashSet<>();
        generateSeatNumbers();
    }

    private void generateSeatNumbers() {
        for (int i = 0; i < rows; i++) {
            generateSeatsForRow(i);
        }
    }

    private void generateSeatsForRow(int i) {
        for (int j = 0; j < seatsPerRow; j++) {
            seats.add(LETTERS[i] + (j + 1));
        }
    }

    public int getShowNumber() {
        return showNumber;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getCancellationWindowInSeconds() {
        return cancellationWindowInSeconds;
    }

    public int getCancellationWindowInMinutes() {
        return cancellationWindowInSeconds/60;
    }

    public List<String> getListOfAllSeatsSorted() {
        List<String> sortedSeats = seats.stream().sorted().collect(Collectors.toList());
        return sortedSeats;
    }

    public void addRow() {
        generateSeatsForRow(rows);
        rows++;
    }
}

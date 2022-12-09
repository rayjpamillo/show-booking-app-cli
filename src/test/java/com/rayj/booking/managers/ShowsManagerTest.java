package com.rayj.booking.managers;

import com.rayj.booking.models.Show;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ShowsManagerTest {

    ShowsManager showsManager;
    Show mockShow;

    @BeforeEach
    void setup(){
        showsManager = ShowsManager.getINSTANCE();
        mockShow = Mockito.mock(Show.class);
    }

    @Test
    void getINSTANCE() {
        assertNotNull(showsManager);
    }

    @Test
    void addShow() {
        int currentSize = showsManager.getShows().size();
        showsManager.addShow(8234, mockShow);

        assertEquals(currentSize+1, showsManager.getShows().size());
    }

    @Test
    void getShow() {
        showsManager.addShow(8235, mockShow);

        assertNotNull(showsManager.getShow(8235));
    }

    @Test
    void removeShow() {
        showsManager.addShow(8236, mockShow);
        showsManager.removeShow(8236);

        assertNull(showsManager.getShow(8236));
    }

    @Test
    void getShows() {
        int currentSize = showsManager.getShows().size();
        showsManager.addShow(8237, mockShow);
        showsManager.addShow(8238, mockShow);

        assertEquals(currentSize+2, showsManager.getShows().size());
    }

    @Test
    void addRow(){
        showsManager.addShow(8331, new Show(8331, 6,9, 360));

        showsManager.addRow(8331);

        assertEquals(7, showsManager.getShow(8331).getRows());
    }
}
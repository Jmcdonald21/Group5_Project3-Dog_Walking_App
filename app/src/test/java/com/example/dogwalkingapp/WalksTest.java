package com.example.dogwalkingapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

class WalksTest {

    @Test
    public void testGetSet() {

        Date testDate = Date.from(Instant.now());

        Walks w = new Walks();

        w.setName("Test Name");
        w.setuID("TestUID");
        w.setDistanceTraveled(55.55);
        w.setStartDate(testDate);
        w.setEndDate(testDate);

        assertEquals("Test Name", w.getName());
        assertEquals("TestUID", w.getuID());
        assertEquals(55.55, w.getDistanceTraveled());
        assertEquals(testDate, w.getStartDate());
        assertEquals(testDate, w.getEndDate());

    }
}

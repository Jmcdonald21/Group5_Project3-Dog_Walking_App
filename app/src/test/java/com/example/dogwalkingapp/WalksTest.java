package com.example.dogwalkingapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
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

        Assertions.assertEquals("Test Name", w.getName());
        Assertions.assertEquals("TestUID", w.getuID());
        Assertions.assertEquals(55.55, w.getDistanceTraveled());
        Assertions.assertEquals(testDate, w.getStartDate());
        Assertions.assertEquals(testDate, w.getEndDate());

    }
}

package com.example.dogwalkingapp;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Testing for the WalkTracking class
 */
public class WalkTrackingTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.dogwalkingapp", appContext.getPackageName());
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}
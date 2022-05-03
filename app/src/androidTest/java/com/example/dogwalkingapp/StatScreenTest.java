package com.example.dogwalkingapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Testing for the Stat Screen Class
 */
@RunWith(AndroidJUnit4.class)
public class StatScreenTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.dogwalkingapp", appContext.getPackageName());
    }
    @Test
    public void thisTest(){

    }
}
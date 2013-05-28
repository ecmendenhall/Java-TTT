package com.cmendenhall;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SwingControllerTest {
    View view = new SwingView();

    @Test
    public void swingControllerShouldExist() {
        SwingController controller = new SwingController(view);

    }

    @Test
    public void gameControllerShouldListenForNewGameButtonClicks() {

    }
}

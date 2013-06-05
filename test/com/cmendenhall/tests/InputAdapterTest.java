package com.cmendenhall.tests;

import com.cmendenhall.views.swing.InputAdapter;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class InputAdapterTest {
    private InputAdapter inputAdapter = new InputAdapter();

    @Test
    public void inputAdapterConstructsCorrectCoordinateStrings() {
        inputAdapter.sendCoordinate(5, 4);
        String constructedInput = inputAdapter.getInput();
        assertEquals("5,4", constructedInput);
    }

    @Test
    public void inputAdapterConstructsCorrectSizeStrings() {
        inputAdapter.sendBoardSize("5");
        String constructedInput = inputAdapter.getInput();
        assertEquals("5", constructedInput);
    }

    @Test
    public void inputAdapterConstructsCorrectPlayerSelection() {
        inputAdapter.sendPlayerSelection(true);
        String constructedInput = inputAdapter.getInput();
        assertEquals("h", constructedInput);

        inputAdapter.sendPlayerSelection(false);
        constructedInput = inputAdapter.getInput();
        assertEquals("c", constructedInput);
    }

    @Test
    public void inputAdapterEnqueuesInputInCorrectOrder() {
        inputAdapter.sendBoardSize("5");
        inputAdapter.sendPlayerSelection(true);
        inputAdapter.sendPlayerSelection(false);
        inputAdapter.sendCoordinate(1, 2);

        assertEquals("5", inputAdapter.getInput());
        assertEquals("h", inputAdapter.getInput());
        assertEquals("c", inputAdapter.getInput());
        assertEquals("1,2", inputAdapter.getInput());
    }
}

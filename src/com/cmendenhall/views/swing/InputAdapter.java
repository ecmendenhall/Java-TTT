package com.cmendenhall.views.swing;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputAdapter {

    private Queue<String> inputQ;

    public InputAdapter() {
        inputQ = new LinkedBlockingQueue<String>();
    }

    private void enqueueInput(String... inputs) {
        for (String input : inputs) {
            inputQ.add(input);
        }
    }

    public void clearInput() {
        inputQ.clear();
    }

    public String getInput() {
        try {
            return inputQ.remove();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

    public void sendCoordinate(Integer row, Integer column) {
        String terminalInput = row.toString() + "," + column.toString();
        enqueueInput(terminalInput);
    }

    public void sendBoardSize(String size) {
        enqueueInput(size);
    }

    public void sendPlayerSelection(boolean humanSelected) {
        String terminalInput = (humanSelected) ? "h" : "c";
        enqueueInput(terminalInput);
    }
}

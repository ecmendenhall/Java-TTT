package com.cmendenhall;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MockTerminalView extends TerminalView {
    private Queue<String> inputQ = new LinkedBlockingQueue<String>();

    public void enqueueInput(String fakeInput) {
        inputQ.add(fakeInput);
    }

    public void clearInput() {
        inputQ.clear();
    }

    @Override
    public String getInput() {
        return inputQ.remove();
    }

}
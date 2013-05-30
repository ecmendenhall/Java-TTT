package com.cmendenhall.mocks;

import com.cmendenhall.views.TerminalView;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MockTerminalView extends TerminalView {
    private Queue<String> inputQ = new LinkedBlockingQueue<String>();

    public void enqueueInput(String... fakeInput) {
        for (String input : fakeInput) {
            inputQ.add(input);
        }
    }

    public void clearInput() {
        inputQ.clear();
    }

    @Override
    public String getInput() {
        return inputQ.remove();
    }

}
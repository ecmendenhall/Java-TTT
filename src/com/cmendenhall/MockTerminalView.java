package com.cmendenhall;

import com.sun.tools.corba.se.idl.constExpr.Terminal;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MockTerminalView extends TerminalView {
    private Queue<String> inputQ = new LinkedBlockingQueue<String>();

    public MockTerminalView() {
    }

    public void pushInput(String fakeInput) {
        inputQ.add(fakeInput);
    }

    public void clearInput() {
        inputQ.clear();
    }

    @Override
    public String getInput() {
        try {
            return inputQ.remove();
        } catch (NoSuchElementException e) {
            System.exit(2);
        }
        return "";
    }
}
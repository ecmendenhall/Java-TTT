package com.cmendenhall;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class OutputRecorder extends PrintStream {
    private Deque<String> outputStack;

    public OutputRecorder(OutputStream outputStream, boolean b, String s) throws UnsupportedEncodingException {
        super(outputStream, b, s);
        outputStack = new LinkedList<String>();
    }

    private void catchOutput(String output) {
        outputStack.addFirst(output);
    }

    public String popLastOutput() {
        return outputStack.removeFirst();
    }

    public String popFirstOutput() {
        return outputStack.removeLast();
    }


    public String peekLastOutput() {
        return outputStack.peekFirst();
    }

    public String peekFirstOutput() {
        return outputStack.peekLast();
    }

    public void discardLastNStrings(int n) {
        for (int i=0; i < n; i++) {
           popLastOutput();
        }
    }

    public void discardFirstNStrings(int n) {
        for (int i=0; i < n; i++) {
            popFirstOutput();
        }
    }

    public void replayAllForwards() {
        String output = popFirstOutput();
        int i = 0;
        while (output != null) {
            System.out.println("Element " + i + ":");
            System.out.println(output);
            i += 1;
            output = popFirstOutput();
        }
    }

    public void replayAllBackwards() {
        String output = popLastOutput();
        int i = outputStack.size();
        while (output != null) {
            System.out.println("Element " + i + ":");
            System.out.println(output);
            i -= 1;
            output = popLastOutput();
        }
    }

    @Override
    public void println(String output) {
        catchOutput(output);
    }

    @Override
    public void print(String output) {
        catchOutput(output);
    }

}

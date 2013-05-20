package com.cmendenhall;

public class TestConsole extends CharStreamConsole {

    public String testInput = "";

    public void setTestInput(String input) {
        testInput = input;
    }

    public String readLine() {
        return testInput;
    }

}

package com.cmendenhall.mocks;

import com.cmendenhall.views.io.CharStreamConsole;

public class TestConsole extends CharStreamConsole {

    public String testInput = "";

    public void setTestInput(String input) {
        testInput = input;
    }

    public String readLine() {
        return testInput;
    }

}

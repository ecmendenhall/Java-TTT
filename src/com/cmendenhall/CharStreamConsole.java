package com.cmendenhall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CharStreamConsole implements IOHandler {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public void print(String output) {
        System.out.println(output);
    }
}

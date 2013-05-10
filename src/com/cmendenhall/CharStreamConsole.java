package com.cmendenhall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CharStreamConsole implements IOHandler {

        // Uses BufferedReader and PrintWriter to mock System.console readLine method.
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public String readLine(String message) {
            try {
                System.out.println(message);
                return reader.readLine();
            } catch (IOException e) {
                throw new IllegalStateException();
            }
        }
    }

package com.cmendenhall;

import java.io.Console;

public class SystemConsole implements IOHandler {
        private final Console console = System.console();

        @Override
        public String readLine(String message) {
            return console.readLine(message);
        }
    }

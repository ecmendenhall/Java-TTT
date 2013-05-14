package com.cmendenhall;

import java.io.Console;

public class SystemConsole implements IOHandler {
        private final Console console = System.console();

        public String readLine() {
            return console.readLine();
        }

        public void print(String output) {
            System.out.println(output);
        }
    }

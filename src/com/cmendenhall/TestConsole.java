package com.cmendenhall;

public class TestConsole extends CharStreamConsole  {

        public String testInput = "";

        public void setTestInput(String input) {
            testInput = input;
        }

        @Override
        public String readLine(String unused) {
            System.out.println(unused);
            return testInput;
        }

    }

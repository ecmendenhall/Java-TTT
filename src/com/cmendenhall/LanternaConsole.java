package com.cmendenhall;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalPosition;

public class LanternaConsole implements IOHandler {
    private Screen screen;
    private ScreenWriter writer;
    private boolean screenStarted = false;

    public LanternaConsole(Screen lanternaScreen) {
        screen = lanternaScreen;
        writer = new ScreenWriter(screen);
        writer.setBackgroundColor(Terminal.Color.BLACK);
        writer.setForegroundColor(Terminal.Color.WHITE);
    }


    public void setUpScreen() {
        if (!screenStarted) {
            screen.startScreen();
            screenStarted = true;
        }
    }

    private void listenForKeys() {
        while (true) {
            Key key = screen.readInput();
            if (key != null && key.getCharacter() == 'R') {
                TerminalPosition cursorPosition = screen.getCursorPosition();
                int column = cursorPosition.getColumn();
                int row = cursorPosition.getRow();
                screen.setCursorPosition(column + 1, row);
                screen.refresh();
            }

            if (key != null && key.getCharacter() == 'L') {
                TerminalPosition cursorPosition = screen.getCursorPosition();
                int column = cursorPosition.getColumn();
                int row = cursorPosition.getRow();
                screen.setCursorPosition(column - 1, row);
                screen.refresh();
            }


            if (key != null && key.getCharacter() == 'U') {
                TerminalPosition cursorPosition = screen.getCursorPosition();
                int column = cursorPosition.getColumn();
                int row = cursorPosition.getRow();
                screen.setCursorPosition(column, row - 1);
                screen.refresh();
            }


            if (key != null && key.getCharacter() == 'D') {
                TerminalPosition cursorPosition = screen.getCursorPosition();
                int column = cursorPosition.getColumn();
                int row = cursorPosition.getRow();
                screen.setCursorPosition(column, row + 1);
                screen.refresh();
            }

            if (key != null && key.getCharacter() == '\n') break;
        }
    }

    public void waitForNewline() {
        while (true) {
            Key key = screen.readInput();
            if (key != null && key.getCharacter() == '\n') break;
        }
    }

    private void readLinePrompt() {
        TerminalPosition position = screen.getCursorPosition();
        int column = position.getColumn();
        int row = position.getRow();
        screen.setCursorPosition(column, row + 1);
    }

    public String readLine() {
        setUpScreen();
        readLinePrompt();
        Key key;
        String buffer = "";
        while(true) {
            key = screen.readInput();
            if (key != null) buffer += key.getCharacter();
            System.out.println(buffer);
            if (key != null && key.getCharacter() == '\n') break;
        }
        return buffer;
    }

    public void print(String output) {
        setUpScreen();
        screen.clear();
        String[] lines = output.split("\n");
        int row = 0;
        for (String line : lines) {
            writer.drawString(0, row, line);
            row++;
        }
        screen.refresh();
    }
}

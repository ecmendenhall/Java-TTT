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

    public void pushCursorUp(int rows) {
        int currentRow = screen.getCursorPosition().getRow();
        int currentColumn = screen.getCursorPosition().getColumn();
        screen.setCursorPosition(currentColumn, currentRow - rows);
    }

    public void pushCursorDown(int rows) {
        int currentRow = screen.getCursorPosition().getRow();
        int currentColumn = screen.getCursorPosition().getColumn();
        screen.setCursorPosition(currentColumn, currentRow + rows);
    }

    private void pushCursorRight(int columns) {
        int currentRow = screen.getCursorPosition().getRow();
        int currentColumn = screen.getCursorPosition().getColumn();
        screen.setCursorPosition(currentColumn + columns, currentRow);
    }

    private void pushCursorLeft(int columns) {
        int currentRow = screen.getCursorPosition().getRow();
        int currentColumn = screen.getCursorPosition().getColumn();
        screen.setCursorPosition(currentColumn - columns, currentRow);
    }

    public String readLine(String prompt) {
        setUpScreen();
        Key key;
        String buffer = "";
        int startRow = screen.getCursorPosition().getRow();
        int startColumn = screen.getCursorPosition().getColumn();
        writer.drawString(startColumn, startRow, prompt);
        pushCursorRight(prompt.length());
        screen.refresh();
        while(true) {
            key = screen.readInput();
            if (key != null) {
                Character keyChar = key.getCharacter();
                int charCode  = keyChar.charValue();

                if (charCode == 66) {
                    try {
                        buffer = buffer.substring(0, buffer.length() - 1);
                        System.out.println(buffer);
                        screen.refresh();
                        screen.clear();
                        pushCursorLeft(1);
                        writer.drawString(startColumn, startRow, prompt);
                        writer.drawString(startColumn, startRow, buffer);
                    } catch (StringIndexOutOfBoundsException e) {
                        buffer = "";
                    }
                }

                else if (charCode == 10) {
                    screen.setCursorPosition(0, 0);
                    break;
                } else {
                    buffer += keyChar;
                    writer.drawString(startColumn, startRow, buffer);
                    pushCursorRight(1);
                    screen.refresh();
                }
            }
        }
        return buffer;
    }

    public String readLine() {
        return readLine("");
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

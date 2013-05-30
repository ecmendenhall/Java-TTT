package com.cmendenhall.views;

import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.board.Board;
import com.cmendenhall.utils.StringLoader;
import com.cmendenhall.views.io.CharStreamConsole;
import com.cmendenhall.views.io.IOHandler;
import com.cmendenhall.views.io.SystemConsole;

import java.util.HashMap;

public class TerminalView implements View {
    private IOHandler io;

    public TerminalView() {
        setUpConsole();
    }

    public TerminalView(IOHandler ioHandler) {
        io = ioHandler;
    }

    private void setUpConsole() {
        if (System.console() == null) {
            System.err.println("No system console found.");
            io = new CharStreamConsole();
        } else {
            io = new SystemConsole();
        }
    }

    public void displayBoard(Board board) {
        String boardString = board.toString();
        io.print(boardString);
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public void endGame() throws GameOverException {
        throw new GameOverException("Game over.");
    }

    public String getInput() {
        String input = io.readLine();
        return input;
    }

    public void reload() {

    }

    public HashMap<String, String> getStrings() {
        HashMap<String, String> viewStrings = new StringLoader().getViewStrings("/viewstrings.properties");
        return viewStrings;
    }
}

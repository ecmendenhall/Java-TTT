package com.cmendenhall.views;

import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.board.Board;

import java.util.HashMap;

public interface View {

    public void displayBoard(Board board);

    public void displayMessage(String message);

    public String getInput();

    public void endGame() throws GameOverException;

    public void reload();

    public HashMap<String, String> getStrings();

}

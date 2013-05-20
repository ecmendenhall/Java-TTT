package com.cmendenhall;

public interface View {

    public void displayBoard(Board board);

    public void displayMessage(String message);

    public String getInput();

    public void endGame() throws GameOverException;

}

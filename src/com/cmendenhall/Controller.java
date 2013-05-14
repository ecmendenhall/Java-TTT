package com.cmendenhall;

public interface Controller {

    public void newGame();

    public void loadGame(Board boardState);

    public void restartGame();

    public void checkForGameOver();

    public void playRound();
}

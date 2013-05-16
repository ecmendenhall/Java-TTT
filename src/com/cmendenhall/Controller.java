package com.cmendenhall;

public interface Controller {

    public void newGame();

    public void loadGame(Board boardState);

    public void restartGame() throws GameOverException;

    public void checkForGameOver() throws GameOverException;

    public void playRound() throws GameOverException;
}

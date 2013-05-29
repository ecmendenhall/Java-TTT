package com.cmendenhall.controllers;

import com.cmendenhall.exceptions.GameOverException;
import com.cmendenhall.board.Board;

public interface Controller {

    public void newGame();

    public void loadGame(Board boardState);

    public void setUp();

    public void startGame() throws GameOverException;

    public void restartGame() throws GameOverException;

    public void checkForGameOver() throws GameOverException;

    public void playRound() throws GameOverException;
}

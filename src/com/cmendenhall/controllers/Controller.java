package com.cmendenhall.controllers;

import com.cmendenhall.board.Board;

public interface Controller {

    public void newGame();

    public void loadGame(Board boardState);

    public void setUp();

    public void startGame() ;

    public void restartGame() ;

    public void checkForGameOver() ;

    public void playRound() ;
}

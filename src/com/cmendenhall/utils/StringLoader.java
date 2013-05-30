package com.cmendenhall.utils;

import java.io.IOException;
import java.util.Properties;

public class StringLoader {
    String welcome;
    String divider;
    String yourMove;
    String yourMoveThreeSquares;
    String playAgain;
    String gameOverDraw;
    String gameOverWin;
    String xWins;
    String oWins;
    String choosePlayerOne;
    String choosePlayerTwo;
    String boardSize;

    public StringLoader() {
    }

    void loadViewStrings() {
        Properties viewStrings = new Properties();
        try {
            viewStrings.load(null.getClass().getResourceAsStream("/viewstrings.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }

        welcome = viewStrings.getProperty("welcome");
        divider = viewStrings.getProperty("divider");
        yourMove = viewStrings.getProperty("yourmove");
        yourMoveThreeSquares = viewStrings.getProperty("yourmovethreesquares");
        playAgain = viewStrings.getProperty("playagain");
        gameOverDraw = viewStrings.getProperty("gameoverdraw");
        gameOverWin = viewStrings.getProperty("gameoverwin");
        xWins = viewStrings.getProperty("xwins");
        oWins = viewStrings.getProperty("owins");
        choosePlayerOne = viewStrings.getProperty("chooseplayerone");
        choosePlayerTwo = viewStrings.getProperty("chooseplayertwo");
        boardSize = viewStrings.getProperty("boardsize");

    }
}
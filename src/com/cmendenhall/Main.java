package com.cmendenhall;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols._;

public class Main {

    public static void main(String[] args) {
        try {
            View view = new TerminalView();
            GameController controller = new GameController(view);

            controller.setUp();
            controller.newGame();
            controller.startGame();
        } catch (GameOverException e) {
            System.exit(0);
        }
    }
}
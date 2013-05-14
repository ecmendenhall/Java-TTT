package com.cmendenhall;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols._;

public class Main {

    public static void main(String[] args) {

        TerminalView view = new TerminalView();
        GameController controller = new GameController(view);
        controller.setPlayerOne(new MinimaxPlayer(X));
        controller.setPlayerTwo(new MinimaxPlayer(O));

        controller.newGame();
        controller.playRound();
    }
}
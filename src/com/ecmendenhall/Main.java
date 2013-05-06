package com.ecmendenhall;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InvalidPlayerException, InvalidMoveException, IOException {

        TerminalView view = new TerminalView();
        GameController controller = new GameController();

        controller.newGame(view);
    }
}
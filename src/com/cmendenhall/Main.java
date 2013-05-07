package com.cmendenhall;


public class Main {

    public static void main(String[] args) throws Exception {

        TerminalView view = new TerminalView();
        GameController controller = new GameController();

        controller.newGame(view);
    }
}
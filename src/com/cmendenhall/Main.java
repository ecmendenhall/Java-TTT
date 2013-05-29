package com.cmendenhall;

public class Main {

    public static void main(String[] args) {
        try {
            SwingView view = new SwingView();
            view.setVisible(true);
            Controller controller = new GameController(view);

            controller.newGame();
            controller.setUp();
            controller.startGame();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
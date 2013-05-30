package com.cmendenhall;

import com.cmendenhall.controllers.Controller;
import com.cmendenhall.controllers.GameController;
import com.cmendenhall.views.swing.SwingView;

import javax.swing.*;

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
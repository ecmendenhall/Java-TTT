package com.cmendenhall;

import com.cmendenhall.controllers.Controller;
import com.cmendenhall.controllers.GameController;
import com.cmendenhall.views.swing.SwingView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            final SwingView[] view = new SwingView[1];
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    view[0] = new SwingView();
                    view[0].setVisible(true);
                }
            });

            Controller controller = new GameController(view[0]);

            controller.newGame();
            controller.setUp();
            controller.startGame();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
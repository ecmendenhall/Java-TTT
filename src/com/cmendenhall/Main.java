package com.cmendenhall;

import com.cmendenhall.controllers.Controller;
import com.cmendenhall.controllers.GameController;
import com.cmendenhall.views.TerminalView;
import com.cmendenhall.views.View;
import com.cmendenhall.views.swing.SwingView;

import javax.swing.*;

public class Main {
    private static Controller controller;

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.setProperty("awt.useSystemAAFontSettings","on");
                System.setProperty("swing.aatext", "true");
                final SwingView[] view = new SwingView[1];
                SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                        view[0] = new SwingView();
                        view[0].setVisible(true);
                }
                });
                controller = new GameController(view[0]);
            } else {
                View view = new TerminalView();
                controller = new GameController(view);
            }
            controller.newGame();
            controller.setUp();
            controller.startGame();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
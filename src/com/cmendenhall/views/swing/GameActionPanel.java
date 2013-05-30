package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameActionPanel extends JPanel {
    private JButton newGameButton;
    private ConfigPanel configPanel;

    public GameActionPanel(ConfigPanel configPanel) {
        this.configPanel = configPanel;
        setName("gameActionPanel");

        newGameButton = new JButton("New game");
        newGameButton.setName("newGameButton");
        add(newGameButton);
        addNewGameListener();
    }

    public void enableNewGameButton() {
        newGameButton.setEnabled(true);
    }

    public void addNewGameListener() {
        ActionListener newGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                configPanel.getView().displayMessage(" ");
                configPanel.sendConfigInput();
                int boardSize = Integer.parseInt(configPanel.boardSize());
                int newWidth = Math.max((boardSize + 1) * 50, 350);
                int newHeight = Math.max((boardSize + 1) * 50 + 200, 400);
                configPanel.getView().resizeWindow(newWidth, newHeight);
                newGameButton.setEnabled(false);
            }
        };
        newGameButton.addActionListener(newGameListener);
    }

}

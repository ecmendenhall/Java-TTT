package com.cmendenhall.views.swing;

import javax.swing.*;
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
    }

    public void enableNewGameButton() {
        newGameButton.setEnabled(true);
    }

    public void disableNewGameButton() {
        newGameButton.setEnabled(false);
    }

    public void setUpNewGameListener(ActionListener newGameListener) {
        newGameButton.addActionListener(newGameListener);
    }

}

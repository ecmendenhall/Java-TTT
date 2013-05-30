package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

public class PlayerConfigPanel extends JPanel {
    private ButtonGroup playerButtons;
    private JRadioButton humanButton;
    private JRadioButton computerButton;

    public PlayerConfigPanel(String label, String name) {
        setName(name);

        playerButtons = new ButtonGroup();

        JLabel playerLabel = new JLabel(label);
        playerLabel.setName("playerLabel");

        humanButton = new JRadioButton("Human", true);
        humanButton.setName("humanButton");

        computerButton = new JRadioButton("Computer", false);
        computerButton.setName("computerButton");

        playerButtons.add(humanButton);
        playerButtons.add(computerButton);

        add(playerLabel,
            humanButton,
            computerButton);
    }

    public Component add(Component... components) {
        for (Component component : components) {
            super.add(component);
        }
        return this;
    }

    public boolean humanSelected() {
        ButtonModel selectedModel = playerButtons.getSelection();
        ButtonModel humanModel = humanButton.getModel();
        return selectedModel.equals(humanModel);
    }
}

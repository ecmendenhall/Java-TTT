package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

public class PlayerConfigPanel extends JPanel {
    private ButtonGroup playerButtons;
    private JRadioButton humanButton;
    private JRadioButton computerButton;

    public PlayerConfigPanel(String label, String name, Color labelColor) {
        setName(name);

        playerButtons = new ButtonGroup();

        JLabel playerLabel = new JLabel(label);
        playerLabel.setName("playerLabel");
        playerLabel.setForeground(labelColor);
        Font labelFont = playerLabel.getFont();
        playerLabel.setFont(new Font(labelFont.getFontName(),
                                     labelFont.getStyle(),
                                     16));

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

    public void disablePlayerSelection() {
        humanButton.setEnabled(false);
        computerButton.setEnabled(false);
    }

    public void enablePlayerSelection() {
        humanButton.setEnabled(true);
        computerButton.setEnabled(true);
    }

    public boolean humanSelected() {
        ButtonModel selectedModel = playerButtons.getSelection();
        ButtonModel humanModel = humanButton.getModel();
        return selectedModel.equals(humanModel);
    }
}

package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    private JLabel label;

    public MessagePanel() {
        setName("messagePanel");

        label = new JLabel("Welcome to Tic-Tac-Toe");
        label.setName("messagePanelLabel");

        Dimension defaultSize = getSize();
        defaultSize.height = 100;
        defaultSize.width = 350;
        setPreferredSize(defaultSize);
        setMaximumSize(defaultSize);

        add(label);
    }

    public JLabel getLabel() {
        return label;
    }

}

package com.cmendenhall;

import javax.swing.*;

public class MessagePanel extends JPanel {

    public MessagePanel() {
        JLabel label = new JLabel("Hey check out this rad message panel");
        label.setName("messagePanelLabel");
        setName("messagePanel");
        System.out.println(getName());
        add(label);
    }

}

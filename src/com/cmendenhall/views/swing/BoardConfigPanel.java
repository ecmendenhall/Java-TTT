package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

public class BoardConfigPanel extends JPanel {
    private SpinnerNumberModel boardSizeSpinnerModel;

    public BoardConfigPanel() {
        setName("boardConfigPanel");

        boardSizeSpinnerModel = new SpinnerNumberModel(3, 3, 10, 1);

        JSpinner boardSizeSpinner = new JSpinner(boardSizeSpinnerModel);
        boardSizeSpinner.setName("boardSizeSpinner");

        Dimension d = boardSizeSpinner.getPreferredSize();
        d.width = 50;
        boardSizeSpinner.setPreferredSize(d);
        boardSizeSpinner.setValue(3);

        JLabel spinnerLabel = new JLabel("Board size:");
        spinnerLabel.setLabelFor(boardSizeSpinner);

        add(spinnerLabel);
        add(boardSizeSpinner);
    }

    public String boardSize() {
        return boardSizeSpinnerModel.getValue().toString();
    }


}

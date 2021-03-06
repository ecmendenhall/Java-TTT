package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

public class BoardConfigPanel extends JPanel {
    private SpinnerNumberModel boardSizeSpinnerModel;
    private JSpinner boardSizeSpinner;

    public BoardConfigPanel() {
        setName("boardConfigPanel");

        JSpinner boardSizeSpinner = loadSpinner();

        JLabel spinnerLabel = new JLabel("Board size:");
        spinnerLabel.setLabelFor(boardSizeSpinner);

        add(spinnerLabel);
        add(boardSizeSpinner);
    }

    private JSpinner loadSpinner() {
        boardSizeSpinnerModel = new SpinnerNumberModel(3, 3, 10, 1);

        boardSizeSpinner = new JSpinner(boardSizeSpinnerModel);
        boardSizeSpinner.setName("boardSizeSpinner");

        Dimension d = boardSizeSpinner.getPreferredSize();
        d.width = 50;
        boardSizeSpinner.setPreferredSize(d);
        boardSizeSpinner.setValue(3);
        return boardSizeSpinner;
    }

    public String boardSize() {
        return boardSizeSpinnerModel.getValue().toString();
    }

    public void disableSpinner() {
        boardSizeSpinner.setEnabled(false);
    }

    public void enableSpinner() {
        boardSizeSpinner.setEnabled(true);
    }

}

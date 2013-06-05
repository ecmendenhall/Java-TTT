package com.cmendenhall.views.swing;

import javax.swing.*;

public abstract class BoardTableStyler {

    public static void applyStyle(JTable boardTable) {

        boardTable.setName("boardTable");
        boardTable.setShowVerticalLines(true);
        boardTable.setShowHorizontalLines(true);
        boardTable.setColumnSelectionAllowed(false);
        boardTable.setRowSelectionAllowed(false);

    }
}

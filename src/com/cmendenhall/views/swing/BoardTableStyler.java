package com.cmendenhall.views.swing;

import javax.swing.*;
import java.awt.*;

public abstract class BoardTableStyler {

    public static void applyStyle(JTable boardTable) {

        boardTable.setName("boardTable");
        boardTable.setShowVerticalLines(true);
        boardTable.setShowHorizontalLines(true);
        boardTable.setGridColor(new Color(80, 80, 80));
        boardTable.setColumnSelectionAllowed(false);
        boardTable.setRowSelectionAllowed(false);
        boardTable.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

    }


}

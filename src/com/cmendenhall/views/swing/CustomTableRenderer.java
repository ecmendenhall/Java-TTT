package com.cmendenhall.views.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomTableRenderer extends JLabel implements TableCellRenderer {

    public CustomTableRenderer() {
        setOpaque(true);
        setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        setFont(new Font("Futura", Font.PLAIN, 32));
        setBackground(new Color(230, 230, 230));
    }

    public Component getTableCellRendererComponent(
            JTable table, Object color,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        String gamePiece = (String)table.getValueAt(row, column);
        setText(gamePiece);
        if (table.isEnabled()) {
            setBackground(Color.WHITE);
        } else {
            setBackground(new Color(232, 232, 232));
        }
        if (gamePiece == "X") {
            setForeground(new Color(130, 216, 0));
        } else if (gamePiece == "O") {
            setForeground(new Color(108, 136, 255));
        }
        return this;
    }
}

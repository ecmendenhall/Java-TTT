package com.cmendenhall.views.swing;

import com.cmendenhall.board.Board;
import com.cmendenhall.board.GameBoard;
import com.cmendenhall.board.Row;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class BoardPanel extends JPanel {
    private JTable boardTable;
    private DefaultTableModel boardData;
    private DefaultTableCellRenderer centeredRenderer;
    private InputAdapter inputAdapter;

    public BoardPanel(SwingView swingView, InputAdapter inputAdapter) {
        this.inputAdapter = inputAdapter;

        setName("boardPanel");

        Dimension maxSize = new Dimension(600, 600);
        setMaximumSize(maxSize);

        centeredRenderer = new DefaultTableCellRenderer();
        centeredRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        boardTable = new JTable();

        BoardTableStyler.applyStyle(boardTable);

        boardData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        boardTable.setModel(boardData);

        loadBoard(new GameBoard());
        formatBoard();

        add(boardTable);
        addBoardClickListener();
    }

    private void formatBoard() {

        TableColumnModel columnModel = boardTable.getColumnModel();
        int numberOfColumns = columnModel.getColumnCount();

        boardTable.setRowHeight(50);

        for (int column=0; column < numberOfColumns; column++) {
            TableColumn currentColumn = columnModel.getColumn(column);
            currentColumn.setPreferredWidth(50);
            currentColumn.setCellRenderer(centeredRenderer);
        }

    }

    public void loadBoard(Board board) {
        setVisible(false);
        java.util.List<Row> rows = board.getRows();
        int boardSize = rows.size();

        boardData.setColumnCount(boardSize);
        boardData.setRowCount(boardSize);

        // read data from board into boardData
        for (int row=0; row < rows.size(); row++) {
            Row boardRow = rows.get(row);
            int[] squares = boardRow.getSquares();
            for (int col=0; col < squares.length; col++) {
                String square = boardRow.intToSymbol(squares[col]);
                boardData.setValueAt(square, row, col);
            }
        }

        formatBoard();
        setVisible(true);
    }

    public void addBoardClickListener() {
        MouseAdapter boardClickListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                Integer row = boardTable.rowAtPoint(event.getPoint());
                Integer column = boardTable.columnAtPoint(event.getPoint());
                if (row >= 0 && column >= 0) {
                    inputAdapter.sendCoordinate(row, column);
                }
            }

        };
        boardTable.addMouseListener(boardClickListener);
    }

}

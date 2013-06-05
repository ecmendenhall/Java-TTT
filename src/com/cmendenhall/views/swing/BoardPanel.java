package com.cmendenhall.views.swing;

import com.cmendenhall.board.Board;
import com.cmendenhall.board.GameBoard;
import com.cmendenhall.board.Row;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardPanel extends JPanel {
    private JTable boardTable;
    private DefaultTableModel boardData;
    private CustomTableRenderer centeredRenderer;
    private InputAdapter inputAdapter;

    public BoardPanel(SwingView swingView, InputAdapter inputAdapter) {
        this.inputAdapter = inputAdapter;

        setName("boardPanel");

        Dimension maxSize = new Dimension(600, 600);
        setMaximumSize(maxSize);

        centeredRenderer = new CustomTableRenderer();

        boardTable = new JTable();
        boardTable.setName("boardTable");

        BoardTableStyler.applyStyle(boardTable);
        disableBoard();

        boardData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        boardTable.setModel(boardData);

        loadBoard(new GameBoard());

        add(boardTable);
        addBoardClickListener();
    }

    public void enableBoard() {
        boardTable.setEnabled(true);
        boardTable.setGridColor(new Color(232, 232, 232));
    }

    public void disableBoard() {
        boardTable.setEnabled(false);
        boardTable.setGridColor(new Color(240, 240, 240));
    }

    private void formatBoard() {

        TableColumnModel columnModel = boardTable.getColumnModel();
        int numberOfColumns = columnModel.getColumnCount();

        boardTable.setMaximumSize(new Dimension(numberOfColumns * 50, numberOfColumns * 50));
        boardTable.setPreferredSize(new Dimension(numberOfColumns * 50, numberOfColumns * 50));
        boardTable.setMinimumSize(new Dimension(numberOfColumns * 50, numberOfColumns * 50));

        boardTable.setRowHeight(50);

        for (int column=0; column < numberOfColumns; column++) {
            TableColumn currentColumn = columnModel.getColumn(column);
            currentColumn.setPreferredWidth(50);
            currentColumn.setMinWidth(50);
            currentColumn.setMaxWidth(50);
            currentColumn.setCellRenderer(centeredRenderer);
        }

    }

    private void readRowsIntoTable(List<Row> rows) {

        int boardSize = rows.size();

        boardData.setColumnCount(boardSize);
        boardData.setRowCount(boardSize);

        formatBoard();

        for (int row=0; row < rows.size(); row++) {
            Row boardRow = rows.get(row);
            int[] squares = boardRow.getSquares();
            for (int col=0; col < squares.length; col++) {
                String square = boardRow.intToSymbol(squares[col]);
                boardData.setValueAt(square, row, col);
            }
        }
    }

    public void loadBoard(Board board) {
        formatBoard();
        final List<Row> rows = board.getRows();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                readRowsIntoTable(rows);
            }
        });

        formatBoard();
    }

    public void addBoardClickListener() {
        MouseAdapter boardClickListener = new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
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

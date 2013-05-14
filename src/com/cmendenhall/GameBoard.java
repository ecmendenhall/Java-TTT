package com.cmendenhall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;
import static com.cmendenhall.TicTacToeSymbols._;

public class GameBoard implements Board {

    final private String divider;
    private List<Row> rows;

    public GameBoard() {
        rows = new ArrayList<Row>();
        rows.add(new Row(_, _, _));
        rows.add(new Row(_, _, _));
        rows.add(new Row(_, _, _));
        divider = makeDivider();
    }

    public GameBoard(Row... boardRows) {
        rows = Arrays.asList(boardRows);
        divider = makeDivider();
    }

    public GameBoard(List<Row> boardRows) {
        rows = boardRows;
        divider = makeDivider();
    }

    private String makeDivider() {
        String divider = "";
        int dividerLength = (int) Math.ceil(3.75 * rows.get(0).numberOfSquares());

        for (int i=0; i < dividerLength; i++) {
            divider += "\u2500";
        }

        divider += "\n";
        return divider;
    }

    public boolean hasWin() {
        return (getWinningRow().getSquare(0) != -1) ? true : false;
    }

    public boolean isFull() {
        for (Row row : rows) {
            if (!row.isFull()) {
                return false;
            }
        }
        return true;
    }

    public int winnerIs() {
        if (hasWin()) {
            return getWinningRow().winner();
        }
        return _;
    }

    public int nextTurn() {
        if (isFull()) return _;
        if (sum() % 3 == 0) {
            return X;
        } else {
            return O;
        }
    }

    @Override
    public String toString() {
        String boardString = "";
        for (int row=0; row < rows.size() - 1; row++) {
            Row thisRow = rows.get(row);
            boardString += thisRow.toString() + divider;
        }
        int lastRow = rows.size() - 1;
        boardString += rows.get(lastRow) + "\n";
        return boardString;
    }

    public Row getWinningRow() {
        List<Row> rows  = getRows();
        List<Row> cols  = getColumns();
        List<Row> diags = getDiagonals();

        for (Row row : rows) {
            if (row.hasWin()) return row;
        }

        for (Row col : cols) {
            if (col.hasWin()) return col;
        }

        for (Row diag : diags) {
            if (diag.hasWin()) return diag;
        }

        int[] error = new int[] {-1};
        return new Row(error);
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Row> getColumns() {
        List<Row> columns = new ArrayList<Row>();
        int numberOfColumns = rows.get(0).numberOfSquares();
        int numberOfRows = rows.size();

        for (int col=0; col < numberOfColumns; col++) {
            int[] newColumnSquares = new int[numberOfRows];

            for (int row=0; row < numberOfRows; row++) {
                newColumnSquares[row] = rows.get(row).getSquare(col);
            }

            Row newColumn = new Row(newColumnSquares);
            columns.add(newColumn);
        }
        return columns;
    }

    public List<Row> getDiagonals() {
        List<Row> diagonals = new ArrayList<Row>();

        int numberOfRows = rows.size();
        int[] topLeftToBottomRight = new int[numberOfRows];
        int[] topRightToBottomLeft = new int[numberOfRows];

        for (int i=0; i < numberOfRows; i++) {
            int j = i + 1;
            topLeftToBottomRight[i] = rows.get(i).getSquare(i);
            topRightToBottomLeft[i] = rows.get(i).getSquare(numberOfRows - j);
        }

        diagonals.add(new Row(topLeftToBottomRight));
        diagonals.add(new Row(topRightToBottomLeft));

        return diagonals;
    }

    public int getSquareByCoordinate(BoardCoordinate coordinate) {
        int coordinateRow = coordinate.getRow();
        Row row = rows.get(coordinateRow);

        int coordinateColumn = coordinate.getColumn();
        int square = row.getSquare(coordinateColumn);

        return square;
    }

    public Board fillSquare(BoardCoordinate coordinate, int player) throws InvalidMoveException {
        return fillSquareByCoordinate(coordinate.getRow(), coordinate.getColumn(), player);
    }

    private Board fillSquareByCoordinate(int row, int column, int player) throws InvalidMoveException {

        if (row > rows.size() || column > rows.size()) {
            throw new InvalidMoveException("Invalid move coordinate.");
        }

        InvalidMoveException fullSquare = new InvalidMoveException("Square is already full.");

        Row fillRow = rows.get(row);
        if (fillRow.squareIsFull(column)) {
            throw fullSquare;
        } else {
            Row newRow = fillRow.fillSquare(column, player);
            List<Row> newRows = new ArrayList<Row>(rows);
            newRows.set(row, newRow);
            return new GameBoard(newRows);
        }
    }

    protected int sum() {
        int sum = 0;
        for (Row row : rows) {
            sum += row.sum();
        }
        return sum;
    }

    private Board nextState(int row, int column) {
        BoardCoordinate moveCoordinate = new BoardCoordinate(row, column);
        Board newBoard;
        try {
            newBoard = fillSquare(moveCoordinate, nextTurn());
        } catch (InvalidMoveException e) {
            newBoard = new GameBoard(rows);
        }
        return newBoard;
    }

    public List<Board> getNextStates() {

        List<Board> newStates = new ArrayList<Board>();

        if (isFull() || hasWin()) {
            return newStates;
        }

        int rowLength = rows.size();
        int columnLength = rows.get(0).numberOfSquares();

        for (int row=0; row < rowLength; row++) {
            Row thisRow = rows.get(row);

            for (int column=0; column < columnLength; column++) {
                if (thisRow.getSquare(column) == _) newStates.add(nextState(row, column));
            }

        }

        return newStates;
    }
}


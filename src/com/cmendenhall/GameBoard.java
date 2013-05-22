package com.cmendenhall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public GameBoard(int n) throws InvalidBoardException {

        if (n < 3 || n > 10) {
            String message = "Please choose a board size between 3 and 10.";
            throw new InvalidBoardException(message);
        }

        rows = new ArrayList<Row>();
        for (int i = 0; i < n; i++) {
            int[] newRowSquares = new int[n];
            Arrays.fill(newRowSquares, _);
            rows.add(new Row(newRowSquares));
        }
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

        for (int i = 0; i < dividerLength; i++) {
            divider += "\u2500";
        }

        divider += "\n";
        return divider;
    }

    @Override
    public String toString() {
        String boardString = "";
        for (int row = 0; row < rows.size() - 1; row++) {
            Row thisRow = rows.get(row);
            boardString += thisRow.toString() + divider;
        }
        int lastRow = rows.size() - 1;
        boardString += rows.get(lastRow) + "\n";
        return boardString;
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

        if (row >= rows.size() || column >= rows.size()) {
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

    public int getSize() {
        return rows.size();
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Row> getColumns() {
        List<Row> rows = getRows();
        List<Row> columns = new ArrayList<Row>();
        int numberOfColumns = rows.get(0).numberOfSquares();
        int numberOfRows = rows.size();

        for (int col = 0; col < numberOfColumns; col++) {
            int[] newColumnSquares = new int[numberOfRows];

            for (int row = 0; row < numberOfRows; row++) {
                newColumnSquares[row] = rows.get(row).getSquare(col);
            }

            Row newColumn = new Row(newColumnSquares);
            columns.add(newColumn);
        }
        return columns;
    }

    public List<Row> getDiagonals() {
        List<Row> rows = getRows();
        List<Row> diagonals = new ArrayList<Row>();

        int numberOfRows = rows.size();
        int[] topLeftToBottomRight = new int[numberOfRows];
        int[] topRightToBottomLeft = new int[numberOfRows];

        for (int i = 0; i < numberOfRows; i++) {
            int j = i + 1;
            topLeftToBottomRight[i] = rows.get(i).getSquare(i);
            topRightToBottomLeft[i] = rows.get(i).getSquare(numberOfRows - j);
        }

        diagonals.add(new Row(topLeftToBottomRight));
        diagonals.add(new Row(topRightToBottomLeft));

        return diagonals;
    }

}


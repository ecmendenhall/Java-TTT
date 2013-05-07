package com.cmendenhall;

import java.util.*;

public class Board extends GameElement {

    // Unicode horizontal box-drawing characters
    final private String HORIZONTAL_LINE = "\u2500\u2500\u2500" +
                                           "\u2500\u2500\u2500" +
                                           "\u2500\u2500\u2500" +
                                           "\u2500\u2500\n";

    final private Row top;
    final private Row middle;
    final private Row bottom;

    public Board() {
        top = new Row();
        middle = new Row();
        bottom = new Row();
    }

    public Board(Row first, Row center, Row last) {
        top = first;
        middle = center;
        bottom = last;
    }

    public Row getTop() {
        return top;
    }

    public Row getMiddle() {
        return middle;
    }

    public Row getBottom() {
        return bottom;
    }

    public Row getWinningRow() {
        Row[]      rows  = getRows();
        Column[]   cols  = getColumns();
        Diagonal[] diags = getDiagonals();

        for (Row row : rows) {
            if (row.hasWin()) return row;
        }

        for (Column col : cols) {
            if (col.hasWin()) return col;
        }

        for (Diagonal diag : diags) {
            if (diag.hasWin()) return diag;
        }

        return new Row(-1, -1, -1);
    }

    public boolean hasWin() {
        return (getWinningRow().getSquare(0) != -1) ? true : false;
    }

    public Row[] getRows() {
        return new Row[] { top, middle, bottom };
    }

    public Column[] getColumns() {
        return new Column[] { new Column(top.getSquare(0),
                                         middle.getSquare(0),
                                         bottom.getSquare(0)),

                              new Column(top.getSquare(1),
                                         middle.getSquare(1),
                                         bottom.getSquare(1)),

                              new Column(top.getSquare(2),
                                         middle.getSquare(2),
                                         bottom.getSquare(2)) };
    }

    public Diagonal[] getDiagonals() {
        return new Diagonal[] { new Diagonal(top.getSquare(0), middle.getSquare(1), bottom.getSquare(2)),
                                new Diagonal(top.getSquare(2), middle.getSquare(1), bottom.getSquare(0))};
    }

    public int getSquareByCoordinate(BoardCoordinate coordinate) {
        return getRows()[coordinate.getRow()].getSquare(coordinate.getColumn());
    }

    public int getSquare(String locationPhrase) throws InvalidCoordinateException {
        BoardCoordinate coordinate = new BoardCoordinate(locationPhrase);
        return getSquareByCoordinate(coordinate);
    }

    public Board fillSquare(BoardCoordinate coordinate, int player) throws InvalidMoveException {
        return fillSquareByCoordinate(coordinate.getRow(), coordinate.getColumn(), player);
    }

    private Board fillSquareByCoordinate(int row, int column, int player) throws InvalidMoveException {

        InvalidMoveException fullSquare = new InvalidMoveException("Square is already full.");

        switch (row) {
            case 0:
                if (top.squareIsFull(column)) {
                    throw fullSquare;
                } else {
                    Row newTop = top.fillSquare(column, player);
                    return new Board(newTop, middle, bottom);
                }

            case 1:
                if (middle.squareIsFull(column)) {
                    throw fullSquare;
                } else {
                    Row newMiddle = middle.fillSquare(column, player);
                    return new Board(top, newMiddle, bottom);
                }

            case 2:
                if (bottom.squareIsFull(column)) {
                    throw fullSquare;
                } else {
                    Row newBottom = bottom.fillSquare(column, player);
                    return new Board(top, middle, newBottom);
                }
        }
        throw new InvalidMoveException("Invalid move coordinate.");
    }

    public boolean isFull() {
        return (top.isFull() && middle.isFull() && bottom.isFull());
    }

    public int winnerIs() {
        if (hasWin()) {
            return getWinningRow().winner();
        }
        return _;
    }

    public boolean moveIsValid(BoardCoordinate coordinate) {
        return getSquareByCoordinate(coordinate) == _;
    }

    protected int sum() {
        return top.sum() + middle.sum() + bottom.sum();
    }

    public int nextTurn() {
        if (isFull()) return _;
        if (sum() % 3 == 0) {
            return X;
        } else {
            return O;
        }
    }

    protected int countEmptySquares() {
        int empty = 0;

        for (int i=0; i < top.getSquares().length; i++) {
            if (top.getSquare(i) == _) empty++;
            if (middle.getSquare(i) == _) empty ++;
            if (bottom.getSquare(i) == _) empty ++;
        }

        return empty;
    }

    @Override
    public String toString() {
        return top.toString()    +
               HORIZONTAL_LINE   +
               middle.toString() +
               HORIZONTAL_LINE   +
               bottom.toString() + "\n";
    }

    public void print() {
        String outputString = toString();
        System.out.print(outputString);
    }

    private Board nextState(int row, int column) throws InvalidMoveException {
        BoardCoordinate moveCoordinate = new BoardCoordinate(row, column);
        Board newboard = fillSquare(moveCoordinate, nextTurn());
        return newboard;
    }

    public List<Board> getNextStates() throws InvalidMoveException {

        List<Board> newStates = new ArrayList<Board>();

        if (isFull() || hasWin()) {
            return newStates;
        }

        for (int i=0; i < top.getSquares().length; i++) {
            if (top.getSquare(i) == _) newStates.add(nextState(0, i));
            if (middle.getSquare(i) == _) newStates.add(nextState(1, i));
            if (bottom.getSquare(i) == _) newStates.add(nextState(2, i));
        }
        return newStates;
    }

    public boolean equals(Board otherBoard) {
        return (top.equals(otherBoard.getTop()) &&
                middle.equals(otherBoard.getMiddle()) &&
                bottom.equals(otherBoard.getBottom()));
    }

}

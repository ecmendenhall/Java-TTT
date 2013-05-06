package com.ecmendenhall;

import java.util.*;

public class Board {
    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;

    final private String HORIZONTAL_LINE = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n";

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
        if (getWinningRow().getSquare(0) != -1) {
            return true;
        } else {
            return false;
        }

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

    public int getSquare(String locationphrase) {
        BoardCoordinate coordinate = new BoardCoordinate(locationphrase);
        return getSquareByCoordinate(coordinate);
    }

    public Board fillSquare(BoardCoordinate coordinate, int player) {
        return fillSquareByCoordinate(coordinate.row, coordinate.column, player);
    }

    public Board fillSquareByCoordinate(int row, int column, int player) {

        Board newboard = new Board(top, middle, bottom);

        switch (row) {

            case 0: newboard.top = top.fillSquare(column, player);
                    return newboard;

            case 1: newboard.middle = middle.fillSquare(column, player);
                    return newboard;

            case 2: newboard.bottom = bottom.fillSquare(column, player);
                    return newboard;
        }

        return null;
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

    public int nextTurn() {
        if (isFull()) return _;
        if (sum() % 3 == 0) {
            return X;
        } else {
            return O;
        }
    }

    protected int sum() {
        return top.sum() + middle.sum() + bottom.sum();
    }

    protected int countEmptySquares() {
        int empty = 0;

        for (int i=0; i < top.getSquares().length; i++) {
            if (top.getSquare(i) == _) empty++;
        }

        for (int i=0; i < middle.getSquares().length; i++) {
            if (middle.getSquare(i) == _) empty++;
        }

        for (int i=0; i < bottom.getSquares().length; i++) {
            if (bottom.getSquare(i) == _) empty++;
        }

        return empty;
    }

    @Override
    public String toString() {
        return top.toString() + HORIZONTAL_LINE + middle.toString() + HORIZONTAL_LINE + bottom.toString() + "\n";
    }

    public void print() {
        String outputstring = toString();
        System.out.print(outputstring);
    }

    public List< Pair<Board, BoardCoordinate> > getNextStates() {

        List< Pair<Board, BoardCoordinate> > newstates = new ArrayList<Pair<Board, BoardCoordinate>>();

        for (int i=0; i < top.squares.length; i++) {
            if (top.squares[i] == _) {
                BoardCoordinate movecoordinate = new BoardCoordinate(0, i);
                Board newboard = fillSquare(movecoordinate, nextTurn());
                Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(newboard,
                                                                                         movecoordinate);
                newstates.add(movepair);
            }
        }

        for (int i=0; i < middle.squares.length; i++) {
            if (middle.squares[i] == _) {
                BoardCoordinate movecoordinate = new BoardCoordinate(1, i);
                Board newboard = fillSquare(movecoordinate, nextTurn());
                Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(newboard,
                                                                                         movecoordinate);
                newstates.add(movepair);
            }
        }

        for (int i=0; i < bottom.squares.length; i++) {
            if (bottom.squares[i] == _) {
                BoardCoordinate movecoordinate = new BoardCoordinate(2, i);
                Board newboard = fillSquare(movecoordinate, nextTurn());
                Pair<Board, BoardCoordinate> movepair = new Pair<Board, BoardCoordinate>(newboard,
                                                                                         movecoordinate);
                newstates.add(movepair);
            }
        }
        return newstates;
    }

}

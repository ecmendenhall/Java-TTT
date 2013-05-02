package com.ecmendenhall;

import java.awt.*;
import java.util.HashMap;

public class Board {
    final private int X = 1;
    final private int O = 2;
    final private int _ = 0;

    Row top;
    Row middle;
    Row bottom;

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

    public Row hasWin() {
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

        return null;
    }

    public Row[] getRows() {
        return new Row[] { top, middle, bottom };
    }

    public Column[] getColumns() {
        return new Column[] { new Column(top.squares[0],
                                         middle.squares[0],
                                         bottom.squares[0]),

                              new Column(top.squares[1],
                                         middle.squares[1],
                                         bottom.squares[1]),

                              new Column(top.squares[2],
                                         middle.squares[2],
                                         bottom.squares[2]) };
    }

    public Diagonal[] getDiagonals() {
        return new Diagonal[] { new Diagonal(top.squares[0], middle.squares[1], bottom.squares[2]),
                                new Diagonal(top.squares[2], middle.squares[1], bottom.squares[0])};
    }

    public int getSquareByCoordinate(BoardCoordinate coordinate) {
        return getRows()[coordinate.row].squares[coordinate.column];
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
        Row winningrow = hasWin();
        if (winningrow != null) {
            return winningrow.winner();
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

    public int sum() {
        return top.sum() + middle.sum() + bottom.sum();
    }

    public int countEmptySquares() {
        int empty = 0;

        for (int i=0; i < top.squares.length; i++) {
            if (top.squares[i] == _) empty++;
        }

        for (int i=0; i < middle.squares.length; i++) {
            if (middle.squares[i] == _) empty++;
        }

        for (int i=0; i < bottom.squares.length; i++) {
            if (bottom.squares[i] == _) empty++;
        }

        return empty;
    }

    public String toString() {
        String boardstring = "";
        return top.toString() + middle.toString() + bottom.toString();
    }

    public void print() {
        String outputstring = toString();
        System.out.print(outputstring);
    }

}

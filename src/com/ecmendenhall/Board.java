package com.ecmendenhall;

import java.awt.*;
import java.util.HashMap;

public class Board {
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

    public boolean hasWin() {
        Row[]      rows  = getRows();
        Column[]   cols  = getColumns();
        Diagonal[] diags = getDiagonals();

        for (Row row : rows) {
            if (row.hasWin()) return true;
        }

        for (Column col : cols) {
            if (col.hasWin()) return true;
        }

        for (Diagonal diag : diags) {
            if (diag.hasWin()) return true;
        }

        return false;
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

    public int getSquareByCoordinate(int row, int column) {
        return getRows()[row].squares[column];
    }

    public int getSquare(String locationphrase) {
        BoardCoordinate coordinate = locationPhraseToCoordinate(locationphrase);
        return getSquareByCoordinate(coordinate.row, coordinate.column);
    }

    private BoardCoordinate locationPhraseToCoordinate(String locationphrase) {
        HashMap<String, Integer> wordmap = new HashMap<String, Integer>();

        wordmap.put("top", 0);
        wordmap.put("middle", 1);
        wordmap.put("bottom", 2);
        wordmap.put("left", 0);
        wordmap.put("center", 1);
        wordmap.put("right", 2);

        String[] words = locationphrase.split(" ");

        int row = wordmap.get(words[0].toLowerCase());
        int column = wordmap.get(words[1].toLowerCase());

        return new BoardCoordinate(row, column);
    }

    public void fillSquare(BoardCoordinate coordinate, int player) {
        fillSquareByCoordinate(coordinate.row, coordinate.column, player);
    }

    public void fillSquareByCoordinate(int row, int column, int player) {
        switch (row) {
            case 0: top.fillSquare(column, player);
                    break;
            case 1: middle.fillSquare(column, player);
                    break;
            case 2: bottom.fillSquare(column, player);
                    break;
        }
    }

    public boolean isFull() {
        return (top.isFull() && middle.isFull() && bottom.isFull());
    }

}

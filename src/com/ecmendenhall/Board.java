package com.ecmendenhall;

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

}

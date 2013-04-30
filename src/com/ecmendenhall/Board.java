package com.ecmendenhall;

import java.util.Arrays;

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

    public Row[] getColumns() {
        return new Row[] { new Row(), new Row(), new Row() };
    }

}

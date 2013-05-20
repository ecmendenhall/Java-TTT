package com.cmendenhall;

import java.util.List;

public interface Board {

    Board fillSquare(BoardCoordinate square, int symbol) throws InvalidMoveException;

    List<Row> getRows();

    List<Row> getColumns();

    List<Row> getDiagonals();
}

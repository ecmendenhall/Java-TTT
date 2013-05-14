package com.cmendenhall;

import java.util.List;

public interface Board {
    boolean hasWin();

    boolean isFull();

    int winnerIs();

    int nextTurn();

    Board fillSquare(BoardCoordinate square, int symbol) throws InvalidMoveException;

    List<Row> getRows();

    @Override
    String toString();

    List<Board> getNextStates();

}

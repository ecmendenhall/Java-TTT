package com.cmendenhall;

public interface Player {

    char getSymbol();

    int getGamePiece();

    Board move(BoardCoordinate move, Board currentBoard) throws InvalidMoveException;

    boolean isHuman();


}

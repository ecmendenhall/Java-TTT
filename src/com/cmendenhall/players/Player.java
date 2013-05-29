package com.cmendenhall.players;

import com.cmendenhall.exceptions.InvalidMoveException;
import com.cmendenhall.board.Board;
import com.cmendenhall.board.BoardCoordinate;

public interface Player {

    char getSymbol();

    int getGamePiece();

    Board move(BoardCoordinate move, Board currentBoard) throws InvalidMoveException;

    boolean isHuman();

}

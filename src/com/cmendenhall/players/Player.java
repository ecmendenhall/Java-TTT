package com.cmendenhall.players;

import com.cmendenhall.board.Board;
import com.cmendenhall.board.BoardCoordinate;
import com.cmendenhall.exceptions.InvalidMoveException;

public interface Player {

    char getSymbol();

    int getGamePiece();

    Board move(BoardCoordinate move, Board currentBoard) throws InvalidMoveException;

    boolean isHuman();

}

package com.cmendenhall.players;

import com.cmendenhall.exceptions.InvalidMoveException;
import com.cmendenhall.board.Board;
import com.cmendenhall.board.BoardCoordinate;

import static com.cmendenhall.TicTacToeSymbols.X;

public class GamePlayer implements Player {

    private final char symbol;
    private final int gamePiece;

    GamePlayer(int playerNumber) {
        gamePiece = playerNumber;
        symbol = (gamePiece == X) ? 'X' : 'O';
    }

    public boolean isHuman() {
        return false;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getGamePiece() {
        return gamePiece;
    }

    public Board move(BoardCoordinate square, Board board) throws InvalidMoveException {
        return board.fillSquare(square, gamePiece);
    }
}

package com.cmendenhall;

import static com.cmendenhall.TicTacToeSymbols.X;
import static com.cmendenhall.TicTacToeSymbols.O;

public class Player {
    private final char symbol;
    private final int gamePiece;

    Player(int playerNumber) {
        gamePiece = playerNumber;
        symbol = (gamePiece == X)? 'X' : 'O';
    }

    public int otherPlayer() {
        return (gamePiece == X)? O : X;
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

    public int scoreBoard(Board board) {
        if (board.winnerIs() == gamePiece) {
            return 1;
        } else if (board.winnerIs() == otherPlayer()) {
            return -1;
        } else {
            return 0;
        }
    }
}
